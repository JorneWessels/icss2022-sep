package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
//		currentContainer = new HANStack<>();
	}
	public AST getAST() {
		return ast;
	}

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet stylesheet = new Stylesheet();
		currentContainer.push(stylesheet);
	}

	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet sheet = (Stylesheet) currentContainer.pop();
		ast.root = sheet;
	}

	@Override
	public void enterStylerule(ICSSParser.StyleruleContext ctx) {
		Stylerule rule = new Stylerule();
		currentContainer.push(rule);
	}

	@Override
	public void exitStylerule(ICSSParser.StyleruleContext ctx) {
		Stylerule rule = (Stylerule) currentContainer.pop();
		currentContainer.peek().addChild(rule);
	}

	@Override
	public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
		TagSelector selector = new TagSelector("TAG");
		currentContainer.push(selector);
	}

	@Override
	public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
		TagSelector tag = (TagSelector) currentContainer.pop();
		currentContainer.peek().addChild(tag);
	}
}