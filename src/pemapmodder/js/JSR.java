/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js;


public final class JSR{
	public final static class Exceptions{
		public final static class IDs{
			public final static int INVALID_TOKEN_GENERIC=0x00;
			public final static int NOT_CLOSED_GENERIC=0x10;
			public final static int BRACE_NOT_CLOSED=0x11;
			public final static int BRACKET_NOT_CLOSED=0x12;
			public final static int SQ_BRACKET_NOT_CLOSED=0x13;
			public final static int QUOTE_NOT_CLOSED=0x18;
			public final static int DBL_QUOTE_NOT_CLOSED=0x19;
			public final static int SLASH_STAR_COMMENT_NOT_CLOSED=0x1a;
		}
		public final static class ToStrings{
			public static String get(int key){
				switch (key) {
				case IDs.INVALID_TOKEN_GENERIC:
					return "Unexpected token";
				case IDs.NOT_CLOSED_GENERIC:
					return "Bracket not closed";
				case IDs.BRACE_NOT_CLOSED:
					return "Unexpected end of script; \"}\" expected";
				case IDs.BRACKET_NOT_CLOSED:
					return "Unexpected end of script; \")\" expected";
				case IDs.SQ_BRACKET_NOT_CLOSED:
					return "Unexpected end of script; \"]\" expected";
				case IDs.QUOTE_NOT_CLOSED:
					return "Unexpected end of script; \"\'\" expected";
				case IDs.DBL_QUOTE_NOT_CLOSED:
					return "Unexpected end of script; \"\"\" expected";
				case IDs.SLASH_STAR_COMMENT_NOT_CLOSED:
					return "Unexpected end of script; \"*/\" expected";
				default:
					return "Exception";
				}
			}
		}
	}
	public final static class Tokens{
		
	}
}
