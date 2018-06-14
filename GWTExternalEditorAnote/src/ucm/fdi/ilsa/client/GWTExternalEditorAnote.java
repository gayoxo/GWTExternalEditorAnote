package ucm.fdi.ilsa.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTExternalEditorAnote implements EntryPoint {
	
	
	private static CompositeDocumentEditionAnote Actual;


	static {
        export();
    }
	
	public GWTExternalEditorAnote() {
		
	}
	
	
	/**
     * Makes our setData method accessible from plain JS
     */
    private static native void export() /*-{
    	
    	$wnd.AnoteSetContext = @ucm.fdi.ilsa.client.GWTExternalEditorAnote::setContext(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZZ)
    	$wnd.AnoteGetIcon = @ucm.fdi.ilsa.client.GWTExternalEditorAnote::getIcon()
    	$wnd.AnotePersist = @ucm.fdi.ilsa.client.GWTExternalEditorAnote::getPersist()
    	$wnd.AnoteisWaitingUpdate = @ucm.fdi.ilsa.client.GWTExternalEditorAnote::isWaitingUpdate()
    	$wnd.AnoteupdateContext = @ucm.fdi.ilsa.client.GWTExternalEditorAnote::updateContext()
    	$wnd.AnotesetWaitingUpdate = @ucm.fdi.ilsa.client.GWTExternalEditorAnote::setWaitingUpdate(Z)
    	
    }-*/;

    public static void setContext(String IdVentana,String contextId,String Height,boolean isgrammar,boolean edit,boolean views,boolean CompleteView) {

			Long contLong=Long.parseLong(contextId);
			Integer heiInteger=Integer.parseInt(Height);
			if (edit)
				Actual=new CompositeDocumentEditionAnote(IdVentana, contLong, heiInteger, isgrammar);
			else
				new CompositeDocumentDescriptionAnote(IdVentana, contLong, heiInteger, CompleteView, isgrammar, views);

		
		
	}
    
    public static String getIcon() {
    	   	return CompositeDocumentDescriptionAnote.getIcon();
	}
    
    public static void getPersist() {
    	if (Actual!=null)
    		Actual.persistJS();
		
		
	}
    
    public static boolean isWaitingUpdate() {
    	if (Actual!=null)
    		return Actual.isWaitingUpdate();
    	else
    		return false;
		
		
	}
    
    public static boolean updateContext() {
    		return false;
		
		
	}
    
    
    public static void setWaitingUpdate(boolean update) {
    	if (Actual!=null)
    		Actual.setWaitingUpdate(update);
    }


	@Override
	public void onModuleLoad() {
		GWT.log("Anote Load");
		
	}
}
