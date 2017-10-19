package com.isuwang.dapeng.maven.plugin;

import com.isuwang.dapeng.code.Scrooge;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Created by jackliang on 2017/10/19.
 */
@Mojo(name="thriftGenerator")
public class GenerateFilePlugin extends AbstractMojo {

    @Parameter(property = "thriftGenerator.sourceFilePath", defaultValue = "/tmp/in")
    private String sourceFilePath;

    @Parameter(property = "thriftGenerator.targetFilePath", defaultValue = "/tmp/out")
    private String targetFilePath;

    @Parameter(property = "thriftGenerator.copyToPath", defaultValue = "./src/main/java/")
    private String copyToPath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println(" sourceFilePath: " + sourceFilePath);
        System.out.println(" targetFilePath: " + targetFilePath);
        System.out.println(" copyToPath: " + copyToPath);

        //-gen java -all -in /Users/jackliang/dev/thrift_gen/in -out /Users/jackliang/dev/thrift_gen/out
        Scrooge.main(new String[]{"-gen","java","-all",
                "-in","/Users/jackliang/dev/thrift_gen/in",
                "-out", "/Users/jackliang/dev/thrift_gen/out"});
    }
}