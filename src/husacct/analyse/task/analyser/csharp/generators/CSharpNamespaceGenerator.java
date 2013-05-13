package husacct.analyse.task.analyser.csharp.generators;

import husacct.analyse.infrastructure.antlr.csharp.CSharpParser;
import java.util.Stack;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class CSharpNamespaceGenerator extends CSharpGenerator {

	private Stack<String> namespaceStack = new Stack<>();

	public String generateModel(String rootParentNamespace, Tree namespaceTree) {
		String namespaceName = getNamespaceName(namespaceTree);
		createPackageModelForEachNamespace(rootParentNamespace); 	

		return namespaceName;
	}

	private String getNamespaceName(Tree namespaceTree) {
		for (int i = 0; i < namespaceTree.getChildCount(); i++) {
			return getQualifiedIdentifiers((CommonTree) namespaceTree.getChild(i));
		}
		throw new ParserException();
	}

	private String getQualifiedIdentifiers(CommonTree tree) {
		String result = "";
		if (tree.getType() == CSharpParser.QUALIFIED_IDENTIFIER) {
			for (int i = 0; i < tree.getChildCount(); i++) {

				result += "." + tree.getChild(i).getText();
				namespaceStack.push(tree.getChild(i).getText());

			}}
		if (result.length() > 0) {
			result = result.substring(1);
		}
		return result;
	}

	private void createPackageModelForEachNamespace(String rootNamespace) {
		String namespaceName;
		String uniqueName;
		String parentNamespace;

		for (int i = namespaceStack.size(); i > 0; i--)
		{
			namespaceName = namespaceStack.peek();
			uniqueName = CSharpGeneratorToolkit.getUniqueName(rootNamespace, CSharpGeneratorToolkit.getParentName(namespaceStack));

			namespaceStack.pop();

			String parentName = CSharpGeneratorToolkit.getParentName(namespaceStack);
			parentNamespace = rootNamespace + CSharpGeneratorToolkit.potentiallyInsertDot(parentName) + parentName;

			modelService.createPackage(uniqueName, parentNamespace, namespaceName);
		}
	}
}
