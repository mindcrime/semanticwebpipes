import org.deri.pipes.ui.*;
import org.deri.pipes.ui.condition.*;
import org.integratedmodelling.zk.diagram.components.Shape;


import org.w3c.dom.Element;

public class PipeNodeFactory implements IPipeNodeFactory {
	/**
	 * @param tagName The xml tag name for the shape
	 * @param x The x position
	 * @param y The y position
	 * @return a new shape, or null if one cannot be created.
	 */
	public Shape createShape(String tagName, int x, int y) {
	    switch(tagName.toLowerCase()){
				case "and":
					return new AndConditionNode(x,y);	    
				case "compare":
					return new CompareConditionNode(x,y);	    
				case "choose":
					return new ChooseNode(x,y);	    
				case "construct":
					return new ConstructNode(x,y);
				case "for":
					return new ForNode(x,y);
				case "htmlfetch":
					return new  HTMLFetchNode(x,y);
				case "html2xml":
					return new  Html2XmlNode(x,y);
				case "http-get":
				    return new HttpGetNode(x,y);
				case "is-empty":
					return new  IsEmptyConditionNode(x,y);
				case "matches":
					return new  RegexMatchesConditionNode(x,y);
				case "not":
					return new  NotConditionNode(x,y);
				case "or":
					return new OrConditionNode(x,y);	    
				case "parameter":
					return new ParameterNode(x,y);
				case "patch-gen":
					return new PatchGeneratorNode(x,y);
				case "patch-exec":
					return new PatchExecutorNode(x,y);
				case "pipe-call":
				    return new PipeCallNode(x,y);
	   			case "rdf-extract":
					return new RDFExtractNode(x,y);
	   			case "rdffetch":
					return new RDFFetchNode(x,y);
				case "rdfsmix":
					return new RDFSMixNode(x,y);
	   			case "replace-text":
					return new ReplaceTextNode(x,y);
				case "select":
					return new SelectNode(x,y);
				case "simplemix":
					return new SimpleMixNode(x,y);
				case "sparqlendpoint":
					return new SPARQLEndpointNode(x,y);
				case "sparqlresultfetch":
					return new SPARQLResultFetchNode(x,y);
				case "smoosher":
					return new SmoosherNode(x,y);
				case "stringify":
					return new StringifyNode(x,y);
				case "text":
					return new TextNode(x,y);
				case "urlbuilder":
					return new URLBuilderNode(x,y);
				case "variable":
					return new VariableNode(x,y);
				case "xslt":
					return new XSLTNode(x,y);
				case "xmlfetch":
					return new XMLFetchNode(x,y);
				case "xquery":
					return new XQueryNode(x,y);
				case "xslfetch":
					return new XSLFetchNode(x,y);
		}
		if(tagName.startsWith("pipe:")){
		   return new PipeCallNode(x,y,tagName.substring(5));
		}
		println("groovy: don't know how to load "+tagName);
		return null;
	}

	@Override
	public PipeNode createPipeNode(Element element, PipeEditor pipeEditor) {
			String tagName = element.getTagName();
			switch(tagName.toLowerCase()){
			    case "and":
			        return AndConditionNode.loadConfig(element,pipeEditor);
				case "compare":
					return CompareConditionNode.loadConfig(element,pipeEditor);	    
			    case "choose":
			        return ChooseNode.loadConfig(element,pipeEditor);
				case "code":
					return OutPipeNode.loadConfig(element,pipeEditor);
				case "construct":    
					return ConstructNode.loadConfig(element,pipeEditor);
				case "for":    
					return ForNode.loadConfig(element,pipeEditor);
				case "htmlfetch":    
					return HTMLFetchNode.loadConfig(element,pipeEditor);
				case "html2xml":    
					return Html2XmlNode.loadConfig(element,pipeEditor);
				case "http-get":
					return HttpGetNode.loadConfig(element,pipeEditor);
				case "is-empty":    
					return IsEmptyConditionNode.loadConfig(element,pipeEditor);
				case "matches":    
					return RegexMatchesConditionNode.loadConfig(element,pipeEditor);
				case "not":    
					return NotConditionNode.loadConfig(element,pipeEditor);
				case "or":    
					return OrConditionNode.loadConfig(element,pipeEditor);
				case "parameter":  
					return ParameterNode.loadConfig(element,pipeEditor);
				case "patch-executor":    
					return PatchExecutorNode.loadConfig(element,pipeEditor);
				case "patch-generator":    
					return PatchGeneratorNode.loadConfig(element,pipeEditor);
				case "pipe-call":    
					return PipeCallNode.loadConfig(element,pipeEditor);
				case "rdf-extract":    
					return RDFExtractNode.loadConfig(element,pipeEditor);
				case "rdffetch":    
					return RDFFetchNode.loadConfig(element,pipeEditor);
				case "replace-text":    
					return ReplaceTextNode.loadConfig(element,pipeEditor);
				case "select":    
					return SelectNode.loadConfig(element,pipeEditor);
				case "simplemix":   
					return SimpleMixNode.loadConfig(element,pipeEditor);
				case "smoosher":  	  
					return SmoosherNode.loadConfig(element,pipeEditor);
				case "sparqlendpoint":    
					return SPARQLEndpointNode.loadConfig(element,pipeEditor);
				case "stringify":
					return StringifyNode.loadConfig(element,pipeEditor);
				case "text":
					return TextNode.loadConfig(element,pipeEditor);
				case "tuplefetch":    
					return TupleQueryResultFetchNode.loadConfig(element,pipeEditor);
				case "urlbuilder":    
					return URLBuilderNode.loadConfig(element,pipeEditor);
				case "variable": 	   
					return VariableNode.loadConfig(element,pipeEditor);
				case "xmlfetch":    
					return XMLFetchNode.loadConfig(element,pipeEditor);
				case "xslfetch":    
					return XSLFetchNode.loadConfig(element,pipeEditor);
				case "xquery":    
					return XQueryNode.loadConfig(element,pipeEditor);
				case "xslt":   
					return XSLTNode.loadConfig(element,pipeEditor);
				case "rdfs":   
					return RDFSMixNode.loadConfig(element,pipeEditor);
				default:
				println("groovy: don't know how to load "+tagName);
					return null;
			}
		}

}
