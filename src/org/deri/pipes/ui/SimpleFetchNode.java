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
package org.deri.pipes.ui;

import org.deri.pipes.utils.XMLUtil;
import org.integratedmodelling.zk.diagram.components.Port;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.zkoss.zul.Textbox;
/**
 * @author Danh Le Phuoc, danh.lephuoc@deri.org
 *
 */
public abstract class SimpleFetchNode extends InPipeNode implements ConnectingInputNode{
	final Logger logger = LoggerFactory.getLogger(SimpleFetchNode.class);
	protected Textbox urlTextbox=null;
	protected Port urlPort=null;

	public SimpleFetchNode(byte portType,int x,int y,String title){
		super(PipePortType.getPType(portType),x,y,200,50);
		wnd.setTitle(title);
		org.zkoss.zul.Label label=new org.zkoss.zul.Label(" URL: ");
        wnd.appendChild(label);
        urlTextbox =new Textbox();
		wnd.appendChild(urlTextbox);
	}
	
	protected void initialize(){
		super.initialize();
		urlPort =createPort(PipePortType.TEXTIN,35,36);
    
	}
	public void onConnected(Port port){
		urlTextbox.setValue("text [wired]");
		urlTextbox.setReadonly(true);
	}
	
	public void onDisconnected(Port port){
		urlTextbox.setValue("");
		urlTextbox.setReadonly(false);
	}
	
	public void setURL(String url){
		urlTextbox.setValue(url);
	}
	
	public Port getURLPort(){
		return urlPort;
	}
	
	public Node getSrcCode(Document doc,boolean config){
		if(getWorkspace()!=null){
			//return if srcCode was created
			
			Element srcCode = doc.createElement(getTagName());
			if (config) setPosition((Element)srcCode);
			
			Element locElm=doc.createElement("location");
			locElm.appendChild(getConnectedCode(doc,urlTextbox,urlPort,config));
			srcCode.appendChild(locElm);
			return srcCode;
		}
		return null;
	}
	

	
	public void _loadConfig(Element elm){		
		Element locElm=XMLUtil.getFirstSubElementByName(elm, "location");
		loadConnectedConfig(locElm, urlPort, urlTextbox);
	}
}
