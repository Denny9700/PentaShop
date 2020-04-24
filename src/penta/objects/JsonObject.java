package penta.objects;

public class JsonObject {

	private String result;
	private String message;
	private Object content;
	
	public JsonObject() {}
	public JsonObject(String result, String message, Object content)
	{
		this.result = result;
		this.message = message;
		this.content = content;
	}
	
	public String getResult	(				) { return result; 			}
	public void setResult	(String result	) { this.result = result; 	}
	
	public String getMessage(				) { return message; 		}
	public void setMessage	(String message	) { this.message = message; }

	public Object getContent(			   	) { return content; 		}
	public void setContent	(Object content	) { this.content = content; }
		
}
