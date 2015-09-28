/*
 * Copyright (c) 2012 - 2015, Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.sbol.editor.io;

import java.io.IOException;

import org.openrdf.query.QueryEvaluationException;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLValidationException;

import com.clarkparsia.sbol.SBOLSPARQLReader;
import com.clarkparsia.sbol.SBOLSPARQLWriter;
import com.clarkparsia.sbol.SublimeSBOLFactory;
import com.clarkparsia.sbol.editor.Registry;
import com.clarkparsia.sbol.editor.sparql.SPARQLEndpoint;

/**
 * 
 * @author Evren Sirin
 */
public class SPARQLDocumentIO implements DocumentIO {
	private final Registry registry;
	private final SPARQLEndpoint endpoint;
	private final String componentURI;
	private final SBOLSPARQLReader reader;
	private final SBOLSPARQLWriter writer;
	
	public SPARQLDocumentIO(Registry registry, String componentURI, boolean validate) {
		this.registry = registry;
		this.endpoint = registry.createEndpoint();
		this.componentURI = componentURI;
		
		reader = SublimeSBOLFactory.createReader(endpoint, validate);
		writer = SublimeSBOLFactory.createWriter(endpoint, validate);
    }
	
	@Override
	public SBOLDocument read() throws SBOLValidationException, IOException {	
		try {
	        return reader.read(componentURI);
        }
        catch (QueryEvaluationException e) {
        	throw new IOException(e);
        }
    }

	@Override
    public void write(SBOLDocument doc) throws SBOLValidationException, IOException {
	    try {
	        writer.write(doc);
        }
        catch (QueryEvaluationException e) {
        	throw new IOException(e);
        }	    
    }

	@Override
    public String toString() {
	    return registry.toString();
    }	
}
