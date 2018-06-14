package ucm.fdi.ilsa.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;

import fdi.ucm.server.interconect.model.OperationalValueTypeJSON;
import fdi.ucm.server.interconect.model.StructureJSON;
import fdi.ucm.server.interconect.model.StructureJSON.TypeOfStructureEnum;

import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

public class TextComment extends DialogBox {


	private static final String SAVE = "Accept";
	private static final String CLEAR = "Clear";
	private static final String CANCEL = "Cancel&Close";
	private ArrayList<ElementTempCoordenadasAnotacion> textSelector;
	private MenuItem mntmGuardar;
	private MenuItem mntmClear;
	private MenuItem mntmCancelar;
	private RichTextArea richTextArea;
	private StructureJSON Tabla;
	private CompositeDocumentEditionAnote PopUpPanelPadre;
	private HashMap<StructureJSON, HashMap<String, StructureJSON>> HashMapUni;
	private ArrayList<StructureJSON> Lista;
//	private Long ContextId;
//	private PanelTextComent PanelTexto;
	private Long Documentid;
private VerticalPanel verticalPanel;
private TextComment Yo;
	
	
	
	public TextComment(ArrayList<ElementTempCoordenadasAnotacion> textSelectorin, StructureJSON elementoClave,
			HashMap<StructureJSON, HashMap<String, StructureJSON>> hashMap
			, CompositeDocumentEditionAnote popUppanelPadre,Long ContextId, Long Documentid
			,String TextoAnotacion, boolean createNow) {

		super(false);
		setAnimationEnabled(true);
		setGlassEnabled(true);
//		CommentPanel.setEstado(true)
		setHTML("New Annotation");
		setSize("100%", "100%");
		Tabla=elementoClave;
//		this.ContextId=ContextId;
		this.Documentid=Documentid;
		PopUpPanelPadre=popUppanelPadre;
		this.textSelector = textSelectorin;
		verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("883px", "329px");
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		HashMapUni=hashMap;
		Yo=this;
		Lista=new ArrayList<StructureJSON>(hashMap.keySet());
		mntmGuardar = new MenuItem(SAVE, false, new Command() {



			public void execute() {
				
				for (int i = 0; i < Lista.size() && i<textSelector.size(); i++) {
					HashMap<String, StructureJSON> tablaposition=HashMapUni.get(Lista.get(i));
					
					Tabla.setValue(richTextArea.getHTML());
					
					if (tablaposition.get(Anotacion.TOP)!=null
							&&
							tablaposition.get(Anotacion.TOP).getTypeOfStructure()==TypeOfStructureEnum.Text
							)
						tablaposition.get(Anotacion.TOP).setValue(Long.toString(textSelector.get(i).getY()));
					
					
					if (tablaposition.get(Anotacion.LEFT)!=null
							&&
							tablaposition.get(Anotacion.LEFT).getTypeOfStructure()==TypeOfStructureEnum.Text
							)
						tablaposition.get(Anotacion.LEFT).setValue(Long.toString(textSelector.get(i).getX()));
						
					
					
					if (tablaposition.get(Anotacion.WIDTH)!=null
							&&
							tablaposition.get(Anotacion.WIDTH).getTypeOfStructure()==TypeOfStructureEnum.Text
							)
					tablaposition.get(Anotacion.WIDTH).setValue(Long.toString(textSelector.get(i).getWidth()));
					
					
					if (tablaposition.get(Anotacion.HEIGHT)!=null
							&&
							tablaposition.get(Anotacion.HEIGHT).getTypeOfStructure()==TypeOfStructureEnum.Text
						)
						tablaposition.get(Anotacion.HEIGHT).setValue(Long.toString(textSelector.get(i).getHeight()));
						
				}
				
				//SALVAR LA INFO
				
				hide();
				PopUpPanelPadre.hidePopUp();
				PopUpPanelPadre.refreshPanel(); 
				//TODO Algun tipo de refresco del panel de anotaciones
			}

			

		});

		menuBar.addItem(mntmGuardar);
		mntmGuardar.setEnabled(false);
		
		if (createNow)
		{
			creaIteracionNecesariaCompleta();
		}
		else
			if (textSelector.size()>Lista.size())
				creaIteracionNecesariaParcial();
			else
				mntmGuardar.setEnabled(true);

		mntmClear = new MenuItem(CLEAR, false, new Command() {

			public void execute() {
//				PanelTexto.getRichTextArea().setText("");
				richTextArea.setText("");
			}
		});

		menuBar.addItem(mntmClear);

		mntmCancelar = new MenuItem(CANCEL, false, new Command() {

			public void execute() {
				if (!richTextArea.getText().isEmpty())
				{
					if (Window.confirm("Are you sure to close the annotation? The changes will not be persist."))
					{
					hide();
					PopUpPanelPadre.hidePopUp();
					}
				}
				else
				{
//				CommentPanel.setEstado(false);
				hide();
				PopUpPanelPadre.hidePopUp();
				//OCULTAR LOS POPUP
//				MainEntryPoint.hidePopUpSelector();
				}
			}
		});

		menuBar.addItem(mntmCancelar);

		
		VerticalPanel verticalPanel2 = new VerticalPanel();
		verticalPanel2.setSize("100%", "100%");
		richTextArea = new RichTextArea();
		richTextArea.setHTML(TextoAnotacion);
		RichTextToolbar toolbar = new RichTextToolbar(richTextArea);
		verticalPanel2.add(toolbar);
		verticalPanel2.add(richTextArea);
		richTextArea.setSize("100%", "300px");
		
		verticalPanel.add(verticalPanel2);
		
		if (hasValues(elementoClave))
		{
		Button Punto = new Button("Edit more info");
			
		Punto.addClickHandler(new ClickHandler() {
			

			@Override
			public void onClick(ClickEvent event) {
				
				setContext(Long.toString(Tabla.getId().get(0)), null, false);
//				ControladorEditor.setNewContext(Tabla.getId().get(0), null, false);
				Yo.hide();
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
		
		verticalPanel.add(Punto);
		}
		


	}
private void creaIteracionNecesariaParcial() {
	PopUpPanelPadre.setWaitingUpdate(true);
	createIterator(Long.toString(Lista.get(0).getId().get(0)),Long.toString(Documentid),"anote",false);
		
	}
private void creaIteracionNecesariaCompleta() {
	PopUpPanelPadre.setWaitingUpdate(true);
	createIterator(Long.toString(Tabla.getId().get(0)),Long.toString(Documentid),"anote",false);
	
}

private static native String createIterator(String contextId, String documentId, String Editor, boolean isgrammar) /*-{
return window.parent.createIterator(contextId,documentId,Editor,isgrammar);
}-*/;



private static native String setContext(String contextId, String Editor, boolean isgrammar) /*-{
return window.parent.setContext(contextId,Editor,isgrammar);
}-*/;


public void setLista(HashMap<StructureJSON, HashMap<String, StructureJSON>> hashMap) {
	HashMapUni=hashMap;
	Lista=new ArrayList<StructureJSON>(hashMap.keySet());
}
public void setLista(StructureJSON key, HashMap<StructureJSON, HashMap<String, StructureJSON>> value) {
	Tabla=key;
	setLista(value);
	mntmGuardar.setEnabled(true);
	
}

	public StructureJSON getTabla() {
		return Tabla;
	}

	
	public ArrayList<ElementTempCoordenadasAnotacion> getTextSelector() {
		return textSelector;
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
