package oeg.rdflicense;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import java.io.StringWriter;
import java.util.List;
import odrlmodel.ODRLRDF;
import odrlmodel.Policy;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

//JSONSIMPLE
import org.json.simple.JSONObject;

/**
 * This class represents a license in RDF
 * @author Victor
 */
public class RDFLicense {

    public static Model model = null;
    String uri = "";
    
    /**
     * Builds an empty RDFlicense
     */
    public RDFLicense()
    {
        
    }
    
    
    public Policy getPolicy()
    {
        String ttl = toTTL();
        return ODRLRDF.getPolicy(ttl);
    }
    
    /**
     * Gets the label for the RDF license, or an empty string if non-existing
     */
    public String getLabel()
    {
        if (model==null)
            return "";
        return RDFUtils.getFirstValue(model, uri, "http://www.w3.org/2000/01/rdf-schema#label");
    }

    /**
     * Gets the URI for the legal code or an empty string if non-existing
     */
    public String getLegalCode()
    {
        if (model==null)
            return "";
        return RDFUtils.getFirstValue(model, uri, "http://creativecommons.org/ns#legalcode");
    }
    
    /**
     * Gets the official URI of the license
     */
    public String getURI()
    {
        if (model==null)
            return "error";
        return RDFUtils.getFirstValue(model, uri, "http://www.w3.org/2000/01/rdf-schema#seeAlso");
    }
    
    
    
    /**
     * Gets the URI of the license
     */
    public String toString()
    {
        return uri;
    }
    
    /**
     * Gets a JSON representation of the most important elements of the license. Incomplete.
     */
    public String toJSON()
    {
        String json ="";
        try {
            JSONObject obj = new JSONObject();
            obj.put("rdf", uri);
            obj.put("uri", getURI());
            obj.put("title", getLabel());
            json = obj.toString();
        } catch (Exception e) {
            json = "error";
        }
        return json;
    }
    
    /**
     * Gets a TTL representation of the RDFLicense
     */
    public String toTTL()
    {
        StringWriter sw = new StringWriter();
        if (model!=null)
        model.write(sw, "TURTLE");
        return sw.toString();
    }
    
    /**
     * Gets an RDFXML representation of the RDFLicense
     */
    public String toRDFXML()
    {
        StringWriter sw = new StringWriter();
        if (model!=null)
            model.write(sw, "RDF/XML");
//        RDFDataMgr.write(sw, model, Lang.RDFXML); //does not work in APPEngine
        return sw.toString();
    }
    
}
