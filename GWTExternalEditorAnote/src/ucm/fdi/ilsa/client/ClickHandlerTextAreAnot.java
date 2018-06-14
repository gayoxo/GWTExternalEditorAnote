package ucm.fdi.ilsa.client;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ScrollPanel;
import fdi.ucm.server.interconect.model.OperationalValueTypeJSON;
import fdi.ucm.server.interconect.model.StructureJSON;
import fdi.ucm.server.interconect.model.StructureJSON.TypeOfStructureEnum;

public class ClickHandlerTextAreAnot implements ClickHandler{

	private StructureJSON valorTexto;
	private RichTextArea Laba;
	private boolean Edition;
//	private Composite AnotacionV;
	private HashMap<StructureJSON, HashMap<String, StructureJSON>> Valores;
	private CompositeDocumentEditionAnote Padre;
	private Button Punto;
	private PopupPanel PP;

	public ClickHandlerTextAreAnot(StructureJSON value, boolean edition
			, CompositeDocumentEditionAnote popUppanelPadre
			, HashMap<StructureJSON, HashMap<String, StructureJSON>> hashMap) {
		valorTexto=value;
		Edition=edition;
		this.Valores=hashMap;
//		this.AnotacionV=Anotacion;
		this.Padre=popUppanelPadre;
	}

	@Override
	public void onClick(ClickEvent arg0) {
		if (!Edition)
		{
		PP=new PopupPanel(true);
		PP.setGlassEnabled(true);
		 ScrollPanel SP = new ScrollPanel();
		SP.setSize("100%", "100%");
		
		HorizontalPanel VP=new HorizontalPanel();
		SP.add(VP);
		
		
		if (hasValues(valorTexto))
		{
		Punto= new Button("See more info");
			
		Punto.addClickHandler(new ClickHandler() {
			

			@Override
			public void onClick(ClickEvent event) {
				PP.hide();
				setContext(Long.toString(valorTexto.getId().get(0)), null, false);
//				ControladorDescripcion.setNewContext(valorTexto.getId().get(0), null, false, false);
				
//				Setcon
//				PopupPanel PPIn=new PopupPanel();
//				
//				InfoWindowOptions options = InfoWindowOptions.create();
//
//				    options.setContent("<button onclick=\"setContext('"+SS.getId().get(0)+"',null,false)\">See more info</button>");
//
//				    InfoWindow iw = InfoWindow.create(options);
//				    iw.open(GMap, Punto);
				
			}
		});	
		
		VP.add(Punto);
		}
		
		
		
		Laba=new RichTextArea();
		Laba.setHTML(valorTexto.getValue());
		Laba.setEnabled(false);
		VP.add(Laba);
		PP.setWidget(SP);
		PP.center();
		if (PP.getOffsetWidth()>Window.getClientWidth()/2)
			SP.setWidth(Window.getClientWidth()/2+"px");
		
		if (PP.getOffsetHeight()>Window.getClientHeight()-100)
			SP.setHeight(Window.getClientHeight()-100+"px");
		}
		else
		{
			//No Hago el promedio, es a pelo pelo
			List<SelectorPanel> PP= new ArrayList<SelectorPanel>();
			for (Entry<StructureJSON, HashMap<String, StructureJSON>> widget : Valores.entrySet())
				{
				SelectorPanel SS=createPop(widget.getValue());
				if (SS!=null)
					PP.add(SS);
				}
			
			ArrayList<ElementTempCoordenadasAnotacion> PPA= new ArrayList<ElementTempCoordenadasAnotacion>();
			for (SelectorPanel elementTempCoordenadasAnotacion : PP) 
				PPA.add(elementTempCoordenadasAnotacion.getSelector());
			
			launchTextCommentPanel(PPA);
		}
		
	}
	
	
	protected void launchTextCommentPanel(ArrayList<ElementTempCoordenadasAnotacion> aRRAT) {
				

				TextComment TC = new TextComment(aRRAT,valorTexto,Valores,Padre,null, null,valorTexto.getValue(),false);
				TC.center();
		
	}
	
	
	private static native String setContext(String contextId, String Editor, boolean isgrammar) /*-{
	 return window.parent.contextId(contextId,Editor,isgrammar);
}-*/;
	
private SelectorPanel createPop(HashMap<String, StructureJSON> tablaposition) {
		
		Long Top=0l;
		Long Left=0l;
		Long Width=0l;
		Long Height=0l;
		
		try {
			if (tablaposition.get(Anotacion.TOP)!=null
					&&
					tablaposition.get(Anotacion.TOP).getTypeOfStructure()==TypeOfStructureEnum.Text
					&&
					tablaposition.get(Anotacion.TOP).getValue() !=null 
					)
				Top=Long.parseLong(tablaposition.get(Anotacion.TOP).getValue());

			
			if (tablaposition.get(Anotacion.LEFT)!=null
					&&
					tablaposition.get(Anotacion.LEFT).getTypeOfStructure()==TypeOfStructureEnum.Text
					&&
					tablaposition.get(Anotacion.LEFT).getValue() !=null 
					)
				Left=Long.parseLong(tablaposition.get(Anotacion.LEFT).getValue());

			
			
			if (tablaposition.get(Anotacion.WIDTH)!=null
					&&
					tablaposition.get(Anotacion.WIDTH).getTypeOfStructure()==TypeOfStructureEnum.Text
					&&
					tablaposition.get(Anotacion.WIDTH).getValue() !=null 
					)
				Width=Long.parseLong(tablaposition.get(Anotacion.WIDTH).getValue());
			
			if (tablaposition.get(Anotacion.HEIGHT)!=null
					&&
					tablaposition.get(Anotacion.HEIGHT).getTypeOfStructure()==TypeOfStructureEnum.Text
					&&
					tablaposition.get(Anotacion.HEIGHT).getValue() !=null 
					)
				Height=Long.parseLong(tablaposition.get(Anotacion.HEIGHT).getValue());

			
			if (Left!=0&&Top!=0&&Width!=0&&Height!=0)
				return new SelectorPanel(Left.intValue(), Top.intValue(), 0, 0, Width.intValue(), Height.intValue());

			else
				return null;
		} catch (Exception e) {
			return null;
		}
		
	
		
	}
	
private boolean hasValues(StructureJSON sS2) {
	if (NotEspe(sS2)&&sS2.getValue()!=null&&!sS2.getValue().isEmpty())
		return true;
	for (StructureJSON sss : sS2.getSons()) {
		
		if (hasValues(sss))
			return true;
		
	}
	return false;
}

private boolean NotEspe(StructureJSON sS2) {
	for (OperationalValueTypeJSON ov : sS2.getShows()) {

	if (ov.getView().toLowerCase().equals("clavy"))
		if (ov.getName().toLowerCase().equals("gmaps"))
			{
			
			if (ov.getDefault().toLowerCase().equals("latitude"))
				return false;
			
			if (ov.getDefault().toLowerCase().equals("longitude"))
				return false;
			}
		if (ov.getName().toLowerCase().equals("editor"))
		{
			return false;
		}
	}
	return true;
}

}
