package com.hansen.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxCommand {
    public void process(String... listCommand){
        var filename = listCommand[0];
        var i = 0;
        try {
            if(listCommand.length > 1) {
                for (var s : listCommand) {
                    if (s.contains("-t")) {
                        filename = renameFile(listCommand[i - 1], listCommand[i + 1]);
                    }
                    if (s.contains("-o")) {
                        moveFile(filename, listCommand[i + 1]);
                    }
                    i++;
                }
            }else if(listCommand.length == 1){
                if(!listCommand[0].equalsIgnoreCase("-h")) {
                    renameFile(listCommand[0], "text");
                }else{
                    System.out.println("Silahkan masukan command seperti contoh berikut");
                    System.out.println("java -jar sample-0.0.1-SNAPSHOT.jar /var/log/nginx/error.log -t json -o /User/johnmayer/Desktop/nginxlog.json");
                    System.out.println("-t ==> Format file yang akan dikonvesi");
                    System.out.println("-o ==> Destinasi file jika ingin dipindah, jika tidak tidak perlu");
                }
            }else{
                System.out.println("Tidak ada perintah");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String  renameFile(String filepath, String fileType) throws IOException, InterruptedException {
        var filepathto = filepath.replace(".log",(fileType.equalsIgnoreCase("json")?".json" : ".txt"));
        var cmd = "mv "+filepath+" "+filepathto;
        executeCommand(cmd);
        return filepathto;
    }

    private void moveFile(String from, String to) throws IOException, InterruptedException {
        var cmd = "mv "+from+" "+to;
        executeCommand(cmd);
    }

    private void executeCommand(String command) throws IOException, InterruptedException {
        System.out.println("Process Move " + command);
        var run = Runtime.getRuntime();
        Process pr = run.exec(command);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while ((line=buf.readLine())!=null) {
            System.out.println(line);
        }
    }
}
