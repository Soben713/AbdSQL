package queryParsers.parsed;

public class ParsedCreateView extends Parsed {
	public ParsedSelect parsedSelect;
	public String viewName;

	public ParsedCreateView(ParsedSelect parsedSelect, String viewName) {
		this.parsedSelect = parsedSelect;
		this.viewName = viewName;
	}

}
