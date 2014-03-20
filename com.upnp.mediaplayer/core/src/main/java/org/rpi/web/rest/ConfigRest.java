package org.rpi.web.rest;

import java.io.FileInputStream;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.rpi.config.Config;
import org.rpi.plugingateway.PluginGateWay;

import sun.org.mozilla.javascript.internal.json.JsonParser;

/**
 * Root resource (exposed at "myresource" path)
 * http://localhost:8090/myapp/myresource
 */
@Path("config")
public class ConfigRest {

    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     * 
     * @return String that will be returned as a text/plain response.
     */
    @Path("getConfig")
    @GET
    @Produces("text/html; charset=utf-8")
    public String getIt() {
        StringBuilder sb = new StringBuilder();
        try {
            Properties pr = new Properties();
            pr.load(new FileInputStream("app.properties"));

            sb.append("{");
            String q = "\"";
            String colon = ":";
            String space = " ";
            String comma = "";
            Enumeration<?> e = pr.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = pr.getProperty(key);
                sb.append(comma + q + convertKey(key) + q + colon + space + q + converValues(value) + q);
                comma = ",";
            }

            sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String convertKey(String key) {
        key = key.replaceAll("\\.", "_");
        return key;

    }

    private String converValues(String value) {
        try {
            //value = value.replace('\\', '/');
            value = URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            log.error("Error creating Encoding JSON",e);
        }
        return value;
    }


	@Path("getStatus")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatus() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("{");
			String q = "\"";
			String colon = ":";
			String space = " ";
			String comma = ",";
			sb.append(q + "version" + q + colon + space + q + Config.version + q);
			sb.append(comma + q + "java_version" + q + colon + space + q + System.getProperty("java.version") + q);
			sb.append(comma + q + "mp_starttime" + q + colon + space + q + Config.getStartTime() + q);
			sb.append(comma + q + "mp_currenttime" + q + colon + space + q + new Date() + q);
			MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
			MemoryUsage heap = memBean.getHeapMemoryUsage();
			MemoryUsage nonHeap = memBean.getNonHeapMemoryUsage();
			sb.append(comma + q + "memory_heap_used" + q + colon + space + q + heap.getUsed() + q);
			sb.append(comma + q + "memory_nonheap_used" + q + colon + space + q + nonHeap.getUsed() + q);
			com.sun.management.OperatingSystemMXBean mxbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			sb.append(comma + q + "cpu_time" + q + colon + space + q + mxbean.getProcessCpuTime() + q);
			sb.append("}");
		} catch (Exception e) {
			log.error("Error creating Status JSON",e);
		}
		return sb.toString();
	}
	
	@Path("setConfig")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setConfig(String msg) {
		try {
			msg = URLDecoder.decode(msg, "UTF-8");
			if(msg.startsWith("="))
			{
				msg=msg.substring(1);
			}
			log.debug("setConfig: " + msg);
			JsonReader reader = Json.createReader(new StringReader(msg));
			JsonObject configObject = reader.readObject();
	        reader.close();
	        log.debug("Name: " + configObject.getString("friendly_name"));
	        Config.friendly_name = configObject.getString("friendly_name");
	        

		} catch (Exception e) {
			log.error("Error creating Status JSON",e);
		}
		return Response.status(200).entity("HELLO").build();
	}
	
	
	@Path("setSleepTimer")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String setSleepTimer(@QueryParam("value") String value)
	{
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(PluginGateWay.getInstance().setSleepTimer(value));
		} catch (Exception e) {
			log.error("Error creating Status JSON",e);
		}
		return sb.toString();
	}
	
	@Path("cancelSleepTimer")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String cancelSleepTimer() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(PluginGateWay.getInstance().cancelSleepTimer());
		} catch (Exception e) {
			log.error("Error creating Status JSON",e);
		}
		return sb.toString();
	}
	
	@Path("getSleepTimer")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSleepTimer() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(PluginGateWay.getInstance().getSleepTimer());
		} catch (Exception e) {
			log.error("Error creating Status JSON",e);
		}
		return sb.toString();
	}
}
