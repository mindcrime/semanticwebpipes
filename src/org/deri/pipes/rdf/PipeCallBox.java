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

import org.deri.pipes.core.Context;
import org.deri.pipes.core.ExecBuffer;
import org.deri.pipes.core.Operator;
import org.deri.pipes.core.Pipe;
import org.deri.pipes.core.internals.Para;
import org.deri.pipes.core.internals.Source;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Calls another pipe with the given parameters.
 * @author robful
 *
 */
@XStreamAlias("pipe-call")
public class PipeCallBox implements Operator{

	@XStreamAsAttribute
	private String pipeid;
	@XStreamImplicit(itemFieldName="para")
	private List<Para> paras;
	/* (non-Javadoc)
	 * @see org.deri.pipes.core.Operator#execute(org.deri.pipes.core.Context)
	 */
	@Override
	public ExecBuffer execute(Context context) throws Exception {
		Pipe pipe = context.getEngine().getStoredPipe(pipeid);
		//TODO: make parameters set on context or pass to execute method.
		if(paras != null){
			for(Para para : paras){
				pipe.setParameter(para.name, para.getValue().expand(context));
			}
		}
		return pipe.execute(context);
	}
	public void setPipeId(String pipeId){
		this.pipeid = pipeId;
	}
	public void addParameter(String name, String value){
		ensureParameters();
		paras.add(new Para(name,value));
	}
	public void addParameter(String name, Source value){
		ensureParameters();
		paras.add(new Para(name,value));
	}
	/**
	 * 
	 */
	private void ensureParameters() {
		if(paras == null){
			paras = new ArrayList<Para>();
		}
	}

}
