package coop.stlma.tech.protocolsn.blogplugin;

import io.micronaut.runtime.Micronaut;

/**
 * Driver class for the blog plugin
 *
 * @author John Meyerin
 */
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}