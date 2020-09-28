package com.finbourne.features;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.cli.*;


public class Main {
    public static void main(String args[]) throws ClassNotFoundException, IOException, NullFeatureValueException, DuplicateFeatureException, URISyntaxException {
        Options options = new Options();

        Option pkg = new Option("p", "package", true, "package name, eg. 'com.finbourne.lusid.tutorials' (which is also the default option) ");
        pkg.setRequired(true);
        options.addOption(pkg);

        Option fName = new Option("f", "filepath", true, "Name of the features file to be created. By default 'features.txt', and will be created in the root sdk folder. Path from the sdk root folder can also be specified in this format <some-dir>/<another-dir>/filename.txt");
        fName.setRequired(true);
        options.addOption(fName);

        // this needs refactoring from this point on. Not sure if try/catch is needed
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;


        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        String packageName = cmd.getOptionValue("package");
        String filepath = cmd.getOptionValue("filepath");

        if(packageName == null) {
            packageName = "com.finbourne.lusid.tutorials";
        }

        if(filepath == null) {
            filepath = "features.txt";
        }

        System.out.println(packageName);
        System.out.println(filepath);

        Path currentDir = Paths.get(System.getProperty("user.dir"));
        // the below is not a good method as it depends on where the code is run from...
        Path rootPath = currentDir.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
        String finalPath = Paths.get(rootPath.toString(), "sdk", filepath).toString();

        FeatureExtractor featureExtractor = new FeatureExtractor();
        FeatureFileWriter featureFileWriter = new FeatureFileWriter();

        List<String> annotations = featureExtractor.getAnnotations(packageName);
        String annotationsFromMethod = String.join("\n", annotations);
        featureFileWriter.writeToFile(annotationsFromMethod, finalPath);
        System.out.println("Done writing to filepath: " + finalPath);
    }
}
