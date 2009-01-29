/*
 * Copyright (c) 2008-2009,
 * 
 * Digital Enterprise Research Institute, National University of Ireland, 
 * Galway, Ireland
 * http://www.deri.org/
 * http://pipes.deri.org/
 *
 * Semantic Web Pipes is distributed under New BSD License.
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  * Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution and 
 *    reference to the source code.
 *  * The name of Digital Enterprise Research Institute, 
 *    National University of Ireland, Galway, Ireland; 
 *    may not be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.deri.pipes.rdf;

import java.util.ArrayList;
import java.util.List;

import org.deri.pipes.core.PipeContext;
import org.deri.pipes.utils.XMLUtil;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.util.RDFInserter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
/**
 * The Construct query is used to create expected triples by using SPARQL-CONSTRUCT query on specified RDF sources. The output of this operator is the result of a SPARQL-CONSTRUCT query executed on the one or more sources. Each RDF Source can be either a constant (directly input as RDF/XML) or another Pipe operator which can output RDF/XML data. There is an optional attribute &quot;uri&quot;. If it is speficfied, then the source data will be placed into an named graph with attribute's value as graph name.
<pre>
Note: Constant RDF/XML text has to be wrapped into a CDATA section.

Syntax template:

&lt;construct&gt;

&lt;source uri=uri&gt;Enter one source syntax here!&lt;/source&gt;

...
&lt;query&gt;
    Enter SPARQL construct query here!
&lt;/query&gt;
&lt;/construct&gt;
</pre>
 *
 */
public class ConstructBox extends AbstractMerge {
	final Logger logger = LoggerFactory.getLogger(ConstructBox.class);
	
    private List<String> graphNames =new ArrayList<String>();
    private String constructQuery;

   
    public void execute(){
    	buffer= new SesameMemoryBuffer();
    	SesameMemoryBuffer tmp=new SesameMemoryBuffer();
    	mergeInputs(tmp);
    	try{    	  
    		tmp.getConnection().prepareGraphQuery(QueryLanguage.SPARQL,constructQuery).evaluate(new RDFInserter(buffer.getConnection()));
    	}
    	catch(Exception e){
    		logger.warn("problem executing construct box",e);
    	}
    	isExecuted=true;
    }

    public void initialize(PipeContext context,Element element){   
    	super.setContext(context);
    	List<Element> sources=XMLUtil.getSubElementByName(element, "source");
    	constructQuery=XMLUtil.getTextFromFirstSubEleByName(element, "query");
    	if((sources.size()<=0)&&(constructQuery==null)){
			logger.warn("Construct operator syntax error at "+element);
		    return;
		}
    	this.setConstructQuery(constructQuery.trim());
    	for(int i=0;i<sources.size();i++){
    		String opID=context.getPipeParser().getSourceOperatorId(sources.get(i));
    		if (null!=opID){
    			addStream(opID);
    	   	    graphNames.add(sources.get(i).getAttribute("uri"));
    		}
    	}    	
     }

	public String getConstructQuery() {
		return constructQuery;
	}

	public void setConstructQuery(String constructQuery) {
		this.constructQuery = constructQuery;
	}
}
