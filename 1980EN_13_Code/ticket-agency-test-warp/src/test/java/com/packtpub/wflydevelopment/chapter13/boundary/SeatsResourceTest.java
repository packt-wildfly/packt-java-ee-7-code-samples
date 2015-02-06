package com.packtpub.wflydevelopment.chapter13.boundary;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.warp.api.RestContext;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.warp.Activity;
import org.jboss.arquillian.warp.Inspection;
import org.jboss.arquillian.warp.Warp;
import org.jboss.arquillian.warp.WarpTest;
import org.jboss.arquillian.warp.servlet.AfterServlet;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
@WarpTest
@RunAsClient
public class SeatsResourceTest {

    @Deployment(testable = true)
    public static WebArchive deployment() {
        return ShrinkWrap.create(MavenImporter.class).loadPomFromFile("pom.xml").importBuildOutput().as(WebArchive.class);
    }

    @ArquillianResource
    private URL contextPath;

    private ResteasyWebTarget target;

    @Before
    public void setUp() {
        final ResteasyClient client = new ResteasyClientBuilder().build();
        this.target = client.target(contextPath + "rest/seat");
    }

    @Test
    public void testasd() {
        Warp.initiate(new Activity() {
            @Override
            public void perform() {
                final String response = target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
                assertNotNull(response);
            }
        }).inspect(new Inspection() {

            private static final long serialVersionUID = 1L;

            @ArquillianResource
            private RestContext restContext;

            @AfterServlet
            public void testGetSeats() {
                assertEquals(200, restContext.getHttpResponse().getStatusCode());
                assertEquals(MediaType.APPLICATION_JSON, restContext.getHttpResponse().getContentType());
                assertNotNull(restContext.getHttpResponse().getEntity());
            }
        });
    }
}