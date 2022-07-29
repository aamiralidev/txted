package txted;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class txted {

    boolean inPlace = false;
    String excludeLines = null;
    boolean caseInsensitive = false;
    boolean skipEven = false;
    boolean skipOdd = false;
    String suffix = null;
    boolean reverse = false;
    int lineNumber = -1;
    String filename = null;
    String data = null;
    String[] lines = null;
    String stderr = "\n";
    boolean operationSpecified = false;
    List<String> processedLines = new ArrayList<>();
    public txted(String[] args){
        if(args.length < 1){
            System.err.println("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
        }else{
            if(args[args.length-1].equals("") || args[args.length-1].isEmpty()){
                System.err.println("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
            }else {
                if(!setFlags(args) || !readFromFile() || !processData()){
                    System.err.println("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
                }else{
                    outputData();
                }
            }
        }
    }


    public boolean setFlags(String[] args){
        List<String> argList = Arrays.asList(args);
        int i;
        for(i=0; i<argList.size()-1; i++){
            switch (argList.get(i)){
                case "-f":
                    inPlace = true;
                    break;
                case "-e":
                    excludeLines = argList.get(i+1);
                    i++;
                    break;
                case "-i":
                    caseInsensitive = true;
                    break;
                case "-s":
                    if(argList.get(i+1).equals("0")){
                        skipEven = true;
                    }else if (argList.get(i+1).equals("1")){
                        skipOdd = true;
                    }else{
                        return false;
                    }
                    i++;
                    break;
                case "-x":
                    suffix = argList.get(i+1);
                    i++;
                    break;
                case "-r":
                    reverse = true;
                    break;
                case "-n":
                    try {
                        lineNumber = Integer.parseInt(argList.get(i+1));
                    }catch (Exception e){
                        return false;
                    }
                    i++;
                    break;
                default:
                    return false;
            }
        }
        try {
            filename = argList.get(i);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public boolean readFromFile(){
        Path fileName = Path.of(filename);
        try {
            data = Files.readString(fileName);
            lines = data.split("\n");
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean processData(){
        if(excludeLines==null && caseInsensitive ||
                excludeLines!=null && excludeLines.equals("") ||
                suffix!=null && suffix.equals("") ||
                lineNumber!=-1 && lineNumber < 1 ){
            return false;
        }
        processedLines.addAll(Arrays.asList(lines));
        if(skipEven){
            operationSpecified = true;
            List<String> oddLines = new ArrayList<>();
            for(int i=0; i<lines.length; i+=2){
                oddLines.add(processedLines.get(i));
            }
            processedLines = oddLines;
        }else if(skipOdd){
            operationSpecified = true;
            List<String> evenLines = new ArrayList<>();
            for(int i=1; i<lines.length; i+=2){
                evenLines.add(processedLines.get(i));
            }
            processedLines = evenLines;
        }
        if(excludeLines!=null){
            operationSpecified = true;
            processedLines.removeIf(line -> {
                if(caseInsensitive)
                    return line.toLowerCase(Locale.ROOT).contains(excludeLines.toLowerCase(Locale.ROOT));
                return line.contains(excludeLines);
            });
        }
        if(suffix!=null){
            operationSpecified = true;
            for(int i=0; i<processedLines.size(); i++){
                processedLines.set(i, processedLines.get(i).strip() + suffix);
            }
        }
        if(reverse){
            operationSpecified = true;
            Collections.reverse(processedLines);
        }
        if(lineNumber > 0){
            operationSpecified = true;
            for(int i=0; i<processedLines.size(); i++){
                processedLines.set(i, "0".repeat(lineNumber-
                        Integer.toString(i).length()) + Integer.toString(i+1) + " " + processedLines.get(i));
            }
        }
        if(inPlace){
            operationSpecified = true;
        }
        return true;
    }

    private void outputData() {
        if(!operationSpecified){
            System.out.println("edited " + filename +  ": file not edited\n" +
                    "stdout: nothing sent to stdout\n" +
                    "stderr:\n" +
                    "Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n " +
                    "integer ] " + filename + "\n");
            return;
        }
        StringBuilder processedData = new StringBuilder();
        if(inPlace){
            for(String line: processedLines){
                if(line.isEmpty()){
                    continue;
                }
                processedData.append(line.strip()).append(System.lineSeparator());
            }
            FileWriter myWriter = null;
            try {
                myWriter = new FileWriter(filename);
                myWriter.write(processedData.toString().strip() + System.lineSeparator());
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            processedData = new StringBuilder();
            for(String line: processedLines){
                if(line.isEmpty()){
                    continue;
                }
                processedData.append(line.strip()).append(System.lineSeparator());
            }
            System.out.print(processedData.toString());
            if(processedData.toString().isEmpty()){
                System.out.println();
            }
        }
    }

}
