package com.isuwang.dapeng.maven.plugin;

import com.isuwang.dapeng.api.Container;
import com.isuwang.dapeng.api.ContainerFactory;
import com.isuwang.dapeng.bootstrap.classloader.ApplicationClassLoader;
import com.isuwang.dapeng.bootstrap.classloader.ContainerClassLoader;
import com.isuwang.dapeng.bootstrap.classloader.CoreClassLoader;
import com.isuwang.dapeng.impl.container.DapengContainerFactorySpiml;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Run Container Plugin
 *
 * @author craneding
 * @date 16/1/25
 */
@Mojo(name = "run", threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST)
public class RunContainerPlugin extends SoaAbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (project == null) {
            throw new MojoExecutionException("not found project.");
        }

        getLog().info("bundle:" + project.getGroupId() + ":" + project.getArtifactId() + ":" + project.getVersion());

        System.setProperty("soa.base", new File(project.getBuild().getOutputDirectory()).getAbsolutePath().replace("/target/classes", ""));
        System.setProperty("soa.run.mode", "maven");

        IsolatedThreadGroup threadGroup = new IsolatedThreadGroup("RunContainerPlugin");
        Thread bootstrapThread = new Thread(threadGroup, () -> {
            try {

                URL[] urls = ((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs();

                List<URL> shareUrls = new ArrayList<>(Arrays.asList(urls));
                Iterator<URL> iterator = shareUrls.iterator();
                while (iterator.hasNext()) {
                    URL url = iterator.next();

                    if (url.getFile().matches("^.*/dapeng-transaction-impl.*\\.jar$")) {
                        iterator.remove();

                        continue;
                    }

                    if (url.getFile().matches("^.*/dapeng-core.*\\.jar$")) {
                        getLog().info(" sharedUrl found dapeng-core.jar");
                        System.out.println(" sout sharedUrl found dapeng-core.jar....");
                    }

//                    if (removeContainer(iterator, url)) continue;
//
//                    if (removeServiceProjectArtifact(iterator, url)) continue;
                }

                List<URL> appUrls = new ArrayList<>(Arrays.asList(urls));
                iterator = appUrls.iterator();
                while (iterator.hasNext()) {
                    URL url = iterator.next();

                    if (removeCore(iterator, url)) continue;
                }

                List<URL> platformUrls = new ArrayList<>(Arrays.asList(urls));
                iterator = platformUrls.iterator();
                while (iterator.hasNext()) {
                    URL url = iterator.next();
                    if (removeServiceProjectArtifact(iterator, url)) continue;
                    if (removeTwitterAndScalaDependency(iterator,url)) continue;
                }

                List<List<URL>> appURLsList = new ArrayList<>();
                appURLsList.add(appUrls);

                CoreClassLoader coreClassLoader = new CoreClassLoader(shareUrls.toArray(new URL[shareUrls.size()]));

                List<ClassLoader> appClassLoaders = appURLsList.stream().map(i ->
                        new ApplicationClassLoader(i.toArray(new URL[i.size()]),coreClassLoader)).collect(Collectors.toList());

                ContainerClassLoader platformClassLoader = new ContainerClassLoader(platformUrls.toArray(new URL[platformUrls.size()]),coreClassLoader);

                System.out.println("------set classloader-------------");
                Thread.currentThread().setContextClassLoader(coreClassLoader);

                new DapengContainerFactorySpiml().createInstance(appClassLoaders);
                Container dapengContainer = ContainerFactory.getContainer();

                dapengContainer.startup();


            } catch (Exception e) {
                Thread.currentThread().getThreadGroup().uncaughtException(Thread.currentThread(), e);
            }
        }, "RunContainerPlugin" + ".main()");
        bootstrapThread.setContextClassLoader(getClassLoader());
        bootstrapThread.start();

        joinNonDaemonThreads(threadGroup);
    }

    private boolean removeContainer(Iterator<URL> iterator, URL url) {
        if (url.getFile().matches("^.*/dapeng-container-impl.*\\.jar$")) {
            iterator.remove();

            return true;
        }
        return false;
    }

    private boolean removeCore(Iterator<URL> iterator, URL url) {

        if (url.getFile().matches("^.*/dapeng-core.*\\.jar$")) {
            System.out.println("app found dapeng-core.jar.. remove it");
            iterator.remove();

            return true;
        }
        return false;
    }

    private boolean removeTwitterAndScalaDependency(Iterator<URL> iterator, URL url){
        if(url.getFile().matches("^.*/twitter.*\\.jar$")) {
            iterator.remove();
            return true;
        }
        if(url.getFile().matches("^.*/scala.*\\.jar$")) {
            iterator.remove();
            return true;
        }
        return false;
    }

    private boolean removeServiceProjectArtifact(Iterator<URL> iterator, URL url) {
        String regex = project.getArtifact().getFile().getAbsolutePath().replaceAll("\\\\", "/");

        if (File.separator.equals("\\")) {
            regex = regex.replace(File.separator, File.separator + File.separator);
        }

        if (url.getFile().matches("^.*" + regex + ".*$")) {
            iterator.remove();

            return true;
        }
        return false;
    }

}
