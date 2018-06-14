/**
 * 
 */
package ucm.fdi.ilsa.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import fdi.ucm.server.interconect.model.DocumentCompleteJSON;
import fdi.ucm.server.interconect.model.GrammarJSON;
import fdi.ucm.server.interconect.model.OperationalValueTypeJSON;
import fdi.ucm.server.interconect.model.StructureJSON;
import fdi.ucm.server.interconect.model.StructureJSON.TypeOfStructureEnum;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class CompositeDocumentEditionAnote{


	private static final String ERROR_GRAMMAR = "Error Context can be applied to a grammar";
	private static final String ERROR_STRUCTURE = "Error Context can be applied because the structure not match";

	private static boolean added = false;
	private Panel PanelPrincipal;
	private String RandomIdVars;
	private int Heigh;
	private boolean Views;
	private Long ContextId;
	private DocumentCompleteJSON Documento;
	private VerticalPanel VPpanel;
	private StructureJSON encontradoText;
	private StructureJSON encontradoTop;
	private StructureJSON encontradoLeft;
	private StructureJSON encontradoHeight;
	private StructureJSON encontradoWidth;
	private StructureJSON encontradoAnchor;
	private HashMap<StructureJSON, HashMap<StructureJSON, HashMap<StructureJSON, HashMap<String, StructureJSON>>>> HashData;
	private ArrayList<ucm.fdi.ilsa.client.SelectorPanel> popUpSelectorNotaLat;
	private boolean Movile;
	private ArrayList<ucm.fdi.ilsa.client.SelectorPanel> popUpSelector;
	private VerticalPanel General;
	private ScrollPanel Panel_Anotacion;
	private DecoratorPanel DP;
	private Image originalBook;
	private int Width;
	private StructureJSON EE;
	private boolean isSelectionMode=false;
	private boolean isShiftSelectionMode=false;
	public static String ERROR_SELECTION_TOO_SMALL="The selection area is too small, please select an area bigger";
	private static final String MAKE_MULTIVALUED = "No more anotations avariable, make it multivalued to create under demand";
	private SelectorPanel popUpSelectoract;
	private VerticalPanel PanelInserActAnota;
	private CompositeDocumentEditionAnote Yo;
	private Boolean HasMulti;
	private boolean WaitingUpdate;
	private TextComment TC;
	private int actualpage;
	private ArrayList<StructureJSON> lista;
	private StructureJSON SuperS;



	public CompositeDocumentEditionAnote(String randomIdVars, Long contextId, int Height, boolean Grammar) {
		RandomIdVars=randomIdVars;
		this.Heigh=Height-32;
		Width=Window.getClientWidth();
		
		actualpage=0;
		

		
		ContextId=contextId;
		
		Yo=this;
		HasMulti=false;
		
Width=Window.getClientWidth();
		
		
		
		int Variacion=315;

		if (Width>515)
			Width=Width-Variacion;
		
		
	com.google.gwt.user.client.ui.RootPanel RP=com.google.gwt.user.client.ui.RootPanel.get(RandomIdVars);
		
		
		VerticalPanel VP = new VerticalPanel();
		VP.setSpacing(20);
		RP.add(VP);
		
		PanelPrincipal=VP;
		

	String SDocumentoS =getVariableBaseJSONOBJS(RandomIdVars);
		
//		Window.alert("hELLO Panel1 "+ SDocumentoS);
		
		JSONObject JSOSucion = (JSONObject) JSONParser.parseStrict(SDocumentoS);
		
//		Window.alert("hELLO Panel2 "+ JSOSucion.toString());
		

		
		
		
//		JSONValue JSOSucionV = JSOSucion.get(JSOSucion.keySet().iterator().next());
//		if (JSOSucionV.isObject()!=null)
//			JSOSucion=JSOSucionV.isObject();
		
		Documento=CreateJSONObject.create(JSOSucion);
		
		if (Grammar)
			{
			setError(ERROR_GRAMMAR);
			return;
			}
		
		
		StructureJSON S=null;
		for (GrammarJSON gramarpos : Documento.getGramatica()) {
			S=gotContext(gramarpos.getListaS(),contextId);
			
			if (S!=null)
				{
				
				SuperS=S;
			
//			if (!S.getClaseOf().equals(contextId))
//				{
//				S=gotContext(gramarpos.getListaS(),S.getClaseOf());				
//				ContextId=S.getClaseOf();
//				contextId=S.getClaseOf();
//				}
			
			
			
				break;
				}
			
		}
		
		
		if (S!=null
				&&isEditorCorrecto(S)
				)
			{
			
			GWT.log("Encontrado: "+S.getName() );
			HasMulti=S.isMultivalued();
			ProcesoEnBloque(contextId,Documento,S);		
			
			}
		else
		{
			setError(ERROR_STRUCTURE);
			return;
		}
		
		
	}
	
	
/*
	private void Proceso_simple(Long contextId, DocumentCompleteJSON documento, StructureJSON s2) {
		Long encontradoLat=0l;
		Long encontradoLong=0l;
		for (StructureJSON hijos : s2.getSons()) {
			if (hijos.getTypeOfStructure()==TypeOfStructureEnum.Text)
				for (OperationalValueTypeJSON ov : hijos.getShows()) {
					if (ov.getView().toLowerCase().equals("clavy"))
						if (ov.getName().toLowerCase().equals("gmaps"))
							{
							
							if (ov.getDefault().toLowerCase().equals("latitude"))
								encontradoLat=hijos.getClaseOf();
							
							if (ov.getDefault().toLowerCase().equals("longitude"))
								encontradoLong=hijos.getClaseOf();
							}
				}
				
	}
		
		
		List<StructureJSON> S=null;
		for (GrammarJSON gramarpos : documento.getGramatica()) {
			S=gotMultivaluedContext(gramarpos.getListaS(),contextId);
			if (S!=null)
				break;
		}
		
		if (S==null)
			{
			setError(ERROR_GRAMMAR);
			return;
			}
		
		
		VerticalPanel ListaV=new VerticalPanel();
		ListaV.setSize("100%", "100%");
		PanelPrincipal.add(ListaV);
		
		VerticalPanel Glue=new VerticalPanel();
		Glue.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Glue.add(new Label(GEOLOCATION_POINTS));
		Glue.setHeight("30px");
		ListaV.add(Glue);

		for (int i = 0; i < S.size(); i++) {
		    StructureJSON punto =S.get(i);
			LatLng L=procesaPoint(punto,encontradoLat,encontradoLong);
			
			VerticalPanel PanelMapaIndi=new VerticalPanel();
			PanelMapaIndi.setWidth("100%");
			ListaV.add(PanelMapaIndi);
			Label LL=new Label(POINT+(i+1));
			PanelMapaIndi.add(LL);
			SimplePanel SPtext=new SimplePanel();
			SPtext.setWidth("100%");
			PanelMapaIndi.add(SPtext);
			PanelMapaIndi.add(Mapa(L,punto,GEOICONRED,encontradoLat,encontradoLong,SPtext));
		}
		

	}

	private Widget Mapa(LatLng late, StructureJSON sS,String Icono,Long encontradoLat,Long encontradoLong, SimplePanel textAreaPanel) {
		SimplePanelMap panel=new SimplePanelMap();
		
		new MapInternalSingle(panel,textAreaPanel,late,sS,Icono,encontradoLat,encontradoLong);
		
        
		return panel;
	}

*/	

	private void ProcesoEnBloque(Long contextId, DocumentCompleteJSON documento, StructureJSON s2) {
		
//		Paginas->Notas*->Posiciones*->Marca
		 HashData=new HashMap<StructureJSON, HashMap<StructureJSON,HashMap<StructureJSON,HashMap<String, StructureJSON>>>>();

		
		List<StructureJSON> S = null;
		for (GrammarJSON gramarpos : documento.getGramatica()) {
			S=gotMultivaluedContext(gramarpos.getListaS(),contextId);
			if (S!=null)
				break;
		}
		
		if (S==null)
			{
			setError(ERROR_STRUCTURE);
			return;
			}
		
		for (StructureJSON punto : S)
			procesaPoint(punto);
		
		//Anote
		popUpSelectorNotaLat=new ArrayList<SelectorPanel>();
		
		Movile=false;

		popUpSelector= new ArrayList<SelectorPanel>();
		
		if (Navigator.getUserAgent().toLowerCase().contains("mobile"))
			Movile=true;
		else
			Movile=false;
		
		General = new VerticalPanel();
		General.setSpacing(5);
		
		PanelPrincipal.add(General);
		General.setSize("100%", Heigh+"px");
		
		Panel_Anotacion = new ScrollPanel();
//		Panel_Anotacion.setWidth(Width+"px");
//		Panel_Anotacion.setHeight(Heigh+"px");
		Panel_Anotacion.setWidth("100%");
		Panel_Anotacion.setHeight("100%");
		General.add(Panel_Anotacion);
		
		
		HorizontalPanel HP = new HorizontalPanel();
		HP.setHeight(Heigh+"px");
		Panel_Anotacion.add(HP);
		
		originalBook = new Image("");
		
		VerticalPanel PanelOriBook=new VerticalPanel();
		HP.add(PanelOriBook);
		
		HorizontalPanel Botones = new HorizontalPanel();
		PanelOriBook.add(Botones);
		
		Button Mas=new Button(">");
		Mas.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				if (actualpage<(lista.size()-1))
					actualpage++;
				
				refreshpage();
				
			}

			
		});
		
		Button Menos=new Button("<");
		Menos.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				if (actualpage>0)
					actualpage--;
				
				refreshpage();
				
			}

			
		});
		
		Botones.add(Menos);
		Botones.add(Mas);
		
		
		DP= new DecoratorPanel();
		PanelOriBook.add(DP);
		DP.add(originalBook);
		

		float He = Heigh;
		float Wi = Width/3;
		
		
		float prop = He / (Heigh-50);
		Wi = (Wi / prop);
		He = (Heigh-50);
		
		if (Wi>(Width/2))
		{
			float prop2 = Wi / (Width/2);
			He = (He / prop2);
			Wi = (Width/2);
		}
		
		originalBook.setSize(Math.round(Wi) + "px", Math.round(He) + "px");
		
		lista=new ArrayList<StructureJSON>(HashData.keySet());
		
		
			
			/*
			 * 
			 * //TODO TESTO NO LO HACE Ya veremos como apañarlo 
			Long IDREsource=null;
			
			
			try {

				
				
				
				IDREsource=Long.parseLong(EE.getValue());
				LoadingPopupPanel.getInstance().setLabelTexto(ConstantsInformation.LOADING_RESOURCE);
				LoadingPopupPanel.getInstance().center();
			serviceOda.LoadResource(IDREsource, new AsyncCallback<ResourceFile>() {
				
				@Override
				public void onSuccess(ResourceFile result) {
					LoadingPopupPanel.getInstance().hide();
					setURL(result.getResourcePath());
					
					
					
				}
				


				@Override
				public void onFailure(Throwable caught) {
					LoadingPopupPanel.getInstance().hide();
					setURL(EE.getValue());
					
				
				}
			});	
			} catch (Exception e) {*/
//		setURL(EE.getValue());
			/*	
			}
			*/
				
		
			ScrollPanel Panel_Anotacion_actuales = new ScrollPanel();
			Panel_Anotacion_actuales.addStyleName("min300");
			Panel_Anotacion_actuales.setHeight(Heigh+"px");
			
			HP.add(Panel_Anotacion_actuales);
			
			PanelInserActAnota=new VerticalPanel();
			Panel_Anotacion_actuales.add(PanelInserActAnota);
			PanelInserActAnota.setWidth("100%");
			
			for (int i = 0; i < lista.size(); i++) 
				if (lista.get(i).getId().get(0).equals(contextId))
					{
					actualpage=i;
					break;
					}
				
			
			
			refreshpage();
			
		
		
		
		//Anote

		/*
		Long encontradoLat=0l;
		Long encontradoLong=0l;
		for (StructureJSON hijos : s2.getSons()) {
			if (hijos.getTypeOfStructure()==TypeOfStructureEnum.Text)
				for (OperationalValueTypeJSON ov : hijos.getShows()) {
					if (ov.getView().toLowerCase().equals("clavy"))
						if (ov.getName().toLowerCase().equals("gmaps"))
							{
							
							if (ov.getDefault().toLowerCase().equals("latitude"))
								encontradoLat=hijos.getClaseOf();
							
							if (ov.getDefault().toLowerCase().equals("longitude"))
								encontradoLong=hijos.getClaseOf();
							}
				}
				
	}
		
		
		List<StructureJSON> S=null;
		for (GrammarJSON gramarpos : documento.getGramatica()) {
			S=gotMultivaluedContext(gramarpos.getListaS(),contextId);
			if (S!=null)
				break;
		}
		
		if (S==null)
			{
			setError(ERROR_GRAMMAR);
			return;
			}
		
		
		
		List<LatLng> lista=new ArrayList<LatLng>();
		
		HashMap<StructureJSON,LatLng> TSI=new HashMap<StructureJSON,LatLng>();
		for (StructureJSON punto : S) {
			LatLng L=procesaPoint(punto,encontradoLat,encontradoLong);
			if (L!=null)
			{
				lista.add(L);
				TSI.put(punto, L);
				}
		}
		
		GWT.log(lista.size()+"");
		
		VerticalPanel Vertila=new VerticalPanel();
		Vertila.setWidth("100%");
		PanelPrincipal.add(Vertila);
		
		VerticalPanel Glue=new VerticalPanel();
		Glue.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Glue.add(new Label(GEOLOCATION_ROUTE));
		Glue.setHeight("30px");
		Vertila.add(Glue);
		
		DockLayoutPanel ListaV = new DockLayoutPanel(Unit.PX);
		
		ListaV.setSize("100%", (Heigh-30)+"px");
		Vertila.add(ListaV);
		
		ScrollPanel ScrolPanel=new ScrollPanel();
		ScrolPanel.setWidth("100%");
		VerticalPanel VP=new VerticalPanel();
		ScrolPanel.add(VP);
		
		for (int i = 0; i < S.size(); i++) {
			
			HorizontalPanel ElementPanelActual = new HorizontalPanel();
			
			StructureJSON hijos = S.get(i);
			ButtonStruc Boton;
			LatLng LL=TSI.get(hijos);
			
			Boton=new ButtonStruc(Disponibles.size()+1,hijos,LL,encontradoLat,encontradoLong);
			
			Boton.setWidth("100%");
			
			Boton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					try {
						ButtonStruc BS=(ButtonStruc)arg0.getSource();
						Actual.removeStyleName("Seleccionado");
						Actual=BS;
						Actual.addStyleName("Seleccionado");
					} catch (Exception e) {
						// falla la aplicacion del estilo, cosa que no deberia fallar nunca
						GWT.log(e.getLocalizedMessage());
						e.printStackTrace();
					}
					
					
				}
			});
				
			
			
			
			Disponibles.add(Boton);	
			
			
			
			Image MoveUp=new ImageButtonStruc(IMGUP,Boton);
			MoveUp.setSize("22px", "22px");
			MoveUp.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					try {
						ImageButtonStruc II=(ImageButtonStruc) event.getSource();
						procesoUPIterador(II);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
				}
			});
			MoveUp.setTitle(PUSH_THIS_BUTTON_MOVE_UP_ITERABLE_ELEMENT);
			ElementPanelActual.add(MoveUp);
			
			Image MoveDown=new ImageButtonStruc(IMGDOWN,Boton);
			MoveDown.setSize("22px", "22px");
			MoveDown.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					ImageButtonStruc II=(ImageButtonStruc) event.getSource();
					procesoDOWNIterador(II);
					
				}
			});
			MoveDown.setTitle(PUSH_THIS_BUTTON_MOVE_DOWN_ITERABLE_ELEMENT);
			ElementPanelActual.add(MoveDown);
			
			
			ElementPanelActual.add(Boton);
			
			
			VP.add(ElementPanelActual);
			
			
			
			
			if (i==0)
				{
				Boton.addStyleName("Seleccionado");
				Actual=Boton;
				}
			
		}
		
		Image botonMas=new Image(ICONOS_EDIT_ADD_PNG);
		
		botonMas.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ControladorEditor.createIteration(Long.toString(ContextId),Long.toString(Documento.getDocumento().getId()),"gmaps",false);
				
			}
		});
		
		VP.add(botonMas);
		
		
		VPpanel=new VerticalPanel();
		
		//SINO NO ME CARGA EL MAPA
		VPpanel.setSize(ListaV.getOffsetWidth()-200+"px", "100%");
		
		
		
		
		
		
		ListaV.addWest(ScrolPanel, 200);
		
		ListaV.add(VPpanel);
				
		RefreshMap();

	*/
	}
	
//	
//	protected void procesoDOWNIterador(ImageButtonStruc iI) {
//		
//		ButtonStruc Encontrado=null;
//		ButtonStruc Siguiente=null;
//		ButtonStruc Buscar=iI.getBoton();
//		for (ButtonStruc botones : Disponibles) {
//			if (botones==Buscar)
//				Encontrado=botones;
//			else
//				{
//				//la primera vez que pasa por aqui encuentra al hermano la segunda no
//				if (Encontrado!=null)
//					Siguiente=botones;
//
//					Encontrado=null;
//				}
//				
//		}
//		
//		if (Encontrado!=null)
//		{
//			Window.alert(YOU_ARE_ON_BOTTON);		
//			return;
//		}
//		
//		
////		ControladorEditor.moveDOWN(Long.toString(Encontrado.getStructElem().getId().get(0)),Long.toString(Buscar.getStructElem().getId().get(0)),Long.toString(Documento.getDocumento().getId()),"gmaps",false);
//		ControladorEditor.moveDOWN(Long.toString(ContextId),Long.toString(Siguiente.getStructElem().getId().get(0)),Long.toString(Buscar.getStructElem().getId().get(0)),Long.toString(Documento.getDocumento().getId()),"gmaps",false);
//	//	RefreshMap();
//		
//	}
	
	
	private void refreshpage() {
		if (lista.size()>0)
		{
		EE=lista.get(actualpage);
		setURL(EE.getValue());
		refreshPanel(); 
		}
		
	}
	
	
	
	protected void setURL(String URL) {
		DP.remove(originalBook);
		originalBook=new Image(URL);
		DP.add(originalBook);
		originalBook.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				Image I = (Image) event.getSource();
				float He = I.getHeight();
				float Wi = I.getWidth();
				
				if (He>(Heigh-50))
					{
					float prop = He / (Heigh-50);
					Wi = (Wi / prop);
					He = (Heigh-50);
					}
				
				
				if (Wi>(Width/2))
					{
						float prop = Wi / (Width/2);
						He = (He / prop);
						Wi = (Width/2);
					}
				
				

				originalBook.setSize(Math.round(Wi) + "px", Math.round(He) +"px");
				// Window.alert("Altura: " + He + "Ancho: " + Wi );
					AddListeners();
			}
		});
		
	
		
		
	}
	
	
	protected void AddListeners() {
		originalBook.addMouseMoveHandler(new MouseMoveHandler() {

			public void onMouseMove(MouseMoveEvent event) {
					if (isSelectionMode == true) {

						popUpSelectoract.setTamagno(event.getX(), event.getY());
						
					} else {
						
						if (isShiftSelectionMode && !event.isShiftKeyDown())
							{
							
							
							ArrayList<ElementTempCoordenadasAnotacion> ARRAT=new ArrayList<ElementTempCoordenadasAnotacion>();
								{
								for (SelectorPanel PPSelect : popUpSelector) {
								
								ARRAT.add(PPSelect.getSelector());
								}
								
							//TODO LANZA POPUP
//							TextComment TC = new TextComment(ARRAT, book);
//							TC.center();
							launchTextCommentPanel(ARRAT);

							
							
							isShiftSelectionMode=false;
							
								}
							
							
							}

					}

			}
		});
		
		
		originalBook.addMouseDownHandler(new MouseDownHandler() {




			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
				event.stopPropagation();
				if (!Movile)
				{
					
					//EN PRIncipio en movil no va

						if (!isSelectionMode
								&& (event.getNativeButton() == NativeEvent.BUTTON_LEFT)) {
							if (!event.isShiftKeyDown()) 
								{
								hidePopUp();
//								for (SelectorPanel SP : popUpSelector) {
//									SP.hide();
//								}
								popUpSelector=new ArrayList<SelectorPanel>();
								}
							popUpSelectoract = new SelectorPanel(event.getX(), event
									.getY(), originalBook.getAbsoluteLeft(),
									originalBook.getAbsoluteTop(), 0, 0);
							popUpSelectoract.show();
							isSelectionMode = true;
								
						}
	
				}
				else
					{
					
					//EN PRIncipio en movil no va
//						if (!isSelectionMode) {
//						popUpSelectoract = new SelectorPanel(event.getX(), event
//								.getY(), originalBook.getAbsoluteLeft(),
//								originalBook.getAbsoluteTop(), 0, 0);
//						popUpSelectoract.show();
//						isSelectionMode = true;					
//						}
//						else
//						{
//						
//							popUpSelectoract.setTamagno(event.getX(), event.getY());
//							if (!selectorvacio(popUpSelectoract))
//								popUpSelector.add(popUpSelectoract);
//							else Window.alert(ERROR_SELECTION_TOO_SMALL);
//							
//							ArrayList<ElementTempCoordenadasAnotacion> ARRAT=new ArrayList<ElementTempCoordenadasAnotacion>();
//			
//								for (SelectorPanel PPSelect : popUpSelector) {
//								
//								ARRAT.add(PPSelect.getSelector());
//
//
//								}
//								
//							if (!ARRAT.isEmpty())
//							{	
//
//								//TODO LANZA POPUP
////								TextComment TC = new TextComment(ARRAT, book);
////								TC.center();
//								Window.alert("wiii");
//launchTextCommentPanel(ARRAT);
//							}
//							
//
//							
//							isSelectionMode = false;
//					}
				

				}
			}

//			private boolean selectorvacio(SelectorPanel popUpSelectoract) {
//				
//				return popUpSelectoract.vacio_();
//			}
		});

		originalBook.addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				event.preventDefault();
				event.stopPropagation();

					if (isSelectionMode){
						

						if (!selectorvacio(popUpSelectoract))
							popUpSelector.add(popUpSelectoract);
						else Window.alert(ERROR_SELECTION_TOO_SMALL);
						
						if (!event.isShiftKeyDown()||!isMultivalued())
						{
						
						ArrayList<ElementTempCoordenadasAnotacion> ARRAT=new ArrayList<ElementTempCoordenadasAnotacion>();
		
							for (SelectorPanel PPSelect : popUpSelector) {
							
							ARRAT.add(PPSelect.getSelector());
							}
						
						if (!ARRAT.isEmpty())
						{	

							launchTextCommentPanel(ARRAT);
						}
						
						isShiftSelectionMode=false;
						
						}else 
						isShiftSelectionMode=true;

						
						isSelectionMode = false;
						
					}
				


				


			}

			private boolean selectorvacio(SelectorPanel popUpSelectoract) {
				
				return popUpSelectoract.vacio_();
			}
		});
		
		
	}
	

//	protected void procesoUPIterador(ImageButtonStruc iI) {
//		
//		ButtonStruc Anterior=null;
//		ButtonStruc Buscar=iI.getBoton();
//		for (ButtonStruc botones : Disponibles) {
//			if (botones==Buscar)
//				break;
//			else
//				Anterior=botones;
//		}
//		
//		if (Anterior==null)
//		{
//			Window.alert(YOU_ARE_ON_TOP);		
//			return;
//		}
//		
//		
//		ControladorEditor.moveUP(Long.toString(ContextId),Long.toString(Anterior.getStructElem().getId().get(0)),Long.toString(Buscar.getStructElem().getId().get(0)),Long.toString(Documento.getDocumento().getId()),"gmaps",false);
//		
//		
//		
//	//	RefreshMap();
//		
//	}

	public void RefreshMap() {
		
		if (VPpanel!=null)
			{
			VPpanel.clear();
			
			SimplePanel panelTA = new SimplePanel();
			panelTA.setSize("100%", "100%");
			
			VPpanel.add(panelTA);

			
			}
		
		

	}
	

	private void procesaPoint(StructureJSON punto) {	
		
		HashMap<StructureJSON, HashMap<StructureJSON, HashMap<String, StructureJSON>>> actualpoint=HashData.get(punto);
		
		if (actualpoint==null) 
			actualpoint=new HashMap<StructureJSON, HashMap<StructureJSON, HashMap<String, StructureJSON>>>();
		
		for (StructureJSON hijos : punto.getSons()) {
			if (hijos.getClaseOf().equals(encontradoText.getClaseOf()))
				{
				HashMap<StructureJSON, HashMap<String, StructureJSON>> Archon=new HashMap<StructureJSON, HashMap<String, StructureJSON>>();
				procesaPoint2(hijos,Archon);
				//TODO AQUI SE CALCULAN LOS HIJOS
				actualpoint.put(hijos, Archon);
				}
		}
		
		HashData.put(punto, actualpoint);
		
	}
	
private void procesaPoint2(StructureJSON punto, HashMap<StructureJSON, HashMap<String, StructureJSON>> archon) {	
		
		
		
		for (StructureJSON hijos : punto.getSons()) {
			if (hijos.getClaseOf().equals(encontradoAnchor.getClaseOf()))
				{
				HashMap<String, StructureJSON> Archon=new  HashMap<String, StructureJSON>();
				procesaPoint3(hijos,Archon);
				//TODO AQUI SE CALCULAN LOS HIJOS
				archon.put(hijos, Archon);
				}
		}
		
	}

private void procesaPoint3(StructureJSON punto, HashMap<String, StructureJSON> archon) {	
	
	
	
	for (StructureJSON hijos : punto.getSons()) {
		if (hijos.getClaseOf().equals(encontradoTop.getClaseOf()))
			archon.put(Anotacion.TOP,hijos);
		if (hijos.getClaseOf().equals(encontradoLeft.getClaseOf()))
			archon.put(Anotacion.LEFT,hijos);
		if (hijos.getClaseOf().equals(encontradoHeight.getClaseOf()))
			archon.put(Anotacion.HEIGHT,hijos);
		if (hijos.getClaseOf().equals(encontradoWidth.getClaseOf()))
			archon.put(Anotacion.WIDTH,hijos);

	}
	
}

	

	private boolean isEditorCorrecto(StructureJSON s) {
		for (OperationalValueTypeJSON ov : s.getShows()) {
			if (ov.getView().toLowerCase().equals("clavy"))
				if (ov.getName().toLowerCase().equals("editor"))
					if (ov.getDefault().toLowerCase().equals("anote")||ov.getDefault().toLowerCase().equals("anote2"))
						return s.getTypeOfStructure()==TypeOfStructureEnum.Resource
								&&isEditorCorrectoChildrens(s)
								;
		}
		return false;
	}

	private boolean isEditorCorrectoChildrens(StructureJSON s) {
		encontradoText=null;
		for (StructureJSON hijos : s.getSons()) 
				if (hijos.getTypeOfStructure()==TypeOfStructureEnum.Text)
					for (OperationalValueTypeJSON ov : hijos.getShows()) 
						if (ov.getView().toLowerCase().equals("clavy"))
							if (ov.getName().toLowerCase().equals("anote"))		
								if (ov.getDefault().toLowerCase().equals("text"))
									{
									encontradoText=hijos;
									break;
									}
					
					
					
		
		return encontradoText!=null&&isEditorCorrectoChildrensText(encontradoText);
	}
	
	
	private boolean isEditorCorrectoChildrensText(StructureJSON s) {
		 encontradoAnchor=null;
		for (StructureJSON hijos : s.getSons()) 
					for (OperationalValueTypeJSON ov : hijos.getShows()) 
						if (ov.getView().toLowerCase().equals("clavy"))
							if (ov.getName().toLowerCase().equals("anote"))		
								if (ov.getDefault().toLowerCase().equals("anchor"))
									{
									encontradoAnchor=hijos;
									break;
									}
					
					
					
		
		return encontradoAnchor!=null&&isEditorCorrectoChildrensAnchor(encontradoAnchor);
	}
	
	private boolean isEditorCorrectoChildrensAnchor(StructureJSON s) {
		encontradoTop=null;
		encontradoLeft=null;
		encontradoHeight=null;
		encontradoWidth=null;
		for (StructureJSON hijos : s.getSons()) 
				if (hijos.getTypeOfStructure()==TypeOfStructureEnum.Text)
					for (OperationalValueTypeJSON ov : hijos.getShows()) 
						if (ov.getView().toLowerCase().equals("clavy"))
							if (ov.getName().toLowerCase().equals("anote"))		
								{
								if (ov.getDefault().toLowerCase().equals(Anotacion.TOP))
									encontradoTop=hijos;
								if (ov.getDefault().toLowerCase().equals(Anotacion.LEFT))
									encontradoLeft=hijos;
								if (ov.getDefault().toLowerCase().equals(Anotacion.HEIGHT))
									encontradoHeight=hijos;
								if (ov.getDefault().toLowerCase().equals(Anotacion.WIDTH))
									encontradoWidth=hijos;
								}
					
					
					
		
		return encontradoTop!=null&&encontradoLeft!=null&&encontradoHeight!=null&&encontradoWidth!=null;
	}

	private void setError(String Texto) {
		PanelPrincipal.add(new Label(Texto));
		
	}
	
	
	private StructureJSON gotContext(List<StructureJSON> listaS, Long contextId) {
		for (StructureJSON structterJSON : listaS) {
			if (structterJSON.getId().get(0).equals(contextId))
				return structterJSON;
			else
				{
				StructureJSON S=gotContext(structterJSON.getSons(),contextId);
				if (S!=null)
					return S;
				}
		}
		return null;
	}
	
	
	private List<StructureJSON> gotMultivaluedContext(List<StructureJSON> listaS, Long contextId) {
		List<StructureJSON> Hermanos=new ArrayList<StructureJSON>();
		for (StructureJSON structterJSON : listaS) {
			if (structterJSON.getId().get(0).equals(contextId))
				{
					for (StructureJSON structureJSON2 : listaS) {
						if (structureJSON2.getClaseOf().equals(SuperS.getClaseOf())&&(SuperS.getFather()==structureJSON2.getFather()||
								(SuperS.getFather()!=null&&structureJSON2.getFather()!=null&&SuperS.getFather().equals(structureJSON2.getFather()))))
							Hermanos.add(structureJSON2);
					}
					
					return Hermanos;
				}
			else
				{
				List<StructureJSON> S=gotMultivaluedContext(structterJSON.getSons(),contextId);
				if (S!=null)
					return S;
				}
		}
		return null;
	}



	private GrammarJSON gotContext(ArrayList<GrammarJSON> gramatica, Long contextId) {
		for (GrammarJSON grammarJSON : gramatica) {
			if (grammarJSON.getId().equals(contextId))
				return grammarJSON;
		}
		return null;
		
	}
	
	
	public static native Panel getContextDiv(String ContextID) /*-{

	$wnd.daletmp = '$wnd.dale = $wnd.VDocExpand'+ContextID;
	eval($wnd.daletmp)
	  return  $wnd.dale;

	}-*/;
	
	
	public static native DocumentCompleteJSON getVariableBase(String documentID2) /*-{
	$wnd.daletmp = '$wnd.dale = $wnd.DocExpand'+documentID2;
eval($wnd.daletmp)
  return  $wnd.dale;	  

}-*/;

	public static String getIcon() {
		return "anote.png";
		
	}

	public static native void setVariableBase(DocumentCompleteJSON DocumentoExpandido, String idrandomdoct) /*-{


	$wnd.tmp=DocumentoExpandido;
	$wnd.str = '$wnd.DocExpand'+idrandomdoct +' = $wnd.tmp';
	console.log($wnd.str);
	eval($wnd.str)

	}-*/;

	public void persistJS() {
		GWT.log(Documento.toString());
		setVariableBase2(Documento,RandomIdVars);
		
	}
	
	
	public int getHeigh() {
		return Heigh;
	}
	

	protected boolean isMultivalued() {
		return HasMulti;
	}
	
	public void hidePopUp()
	{
		for (SelectorPanel widget : popUpSelector)
			widget.hide();
		
		
		popUpSelector=new ArrayList<SelectorPanel>();
		
	}
	
	
	protected void launchTextCommentPanel(ArrayList<ElementTempCoordenadasAnotacion> aRRAT) {
		boolean found=false;
		HashMap<StructureJSON, HashMap<StructureJSON, HashMap<String, StructureJSON>>> TablaHoja = HashData.get(EE);
		for (Entry<StructureJSON, HashMap<StructureJSON, HashMap<String, StructureJSON>>> elementoClave : TablaHoja.entrySet()) {
			if (elementoClave.getKey().getValue()!=null&&elementoClave.getKey().getValue().isEmpty())
			{
				
				for (ElementTempCoordenadasAnotacion elementTempCoordenadasAnotacion : aRRAT) 
					Promedia_coor(elementTempCoordenadasAnotacion);
				
				
				TC = new TextComment(aRRAT,elementoClave.getKey(),elementoClave.getValue(),Yo,ContextId,Documento.getDocumento().getId(),"",false);
				TC.center();
				found=true;
				break;
			}
		}
		
		if (!found)
			{
			
			if (EE.isMultivalued())
			{
				
				for (ElementTempCoordenadasAnotacion elementTempCoordenadasAnotacion : aRRAT) 
					Promedia_coor(elementTempCoordenadasAnotacion);
				
				
			TC = new TextComment(aRRAT,TablaHoja.entrySet().iterator().next().getKey(),new HashMap<StructureJSON, HashMap<String,StructureJSON>>(),Yo,ContextId,Documento.getDocumento().getId(),"",true);
			TC.center();
			}
			else{
				Window.alert(MAKE_MULTIVALUED);
				
			hidePopUp();
			}
			
			
			
				
				
			}

		
		
		
		
	}
	
	
	public void refreshPanel() {
		PanelInserActAnota.clear();
		for (Entry<StructureJSON, HashMap<StructureJSON, HashMap<String, StructureJSON>>> anotaciones : HashData.get(EE).entrySet()) {
			if (anotaciones.getKey().getValue()!=null&&!anotaciones.getKey().getValue().isEmpty())
			{
				RichTextArea TA=new RichTextArea();
				TA.setHTML(anotaciones.getKey().getValue());
				
				ClickHandlerTextAreAnot Activet = new ClickHandlerTextAreAnot(anotaciones.getKey(),PermisoEscritura(),Yo,anotaciones.getValue());
				
				TA.addClickHandler(Activet);
				TA.setSize("100%", "300px");
				TA.setEnabled(false);
				
				MouseOverHandlerAreAnot ActivetMouse = new MouseOverHandlerAreAnot(anotaciones.getValue(),popUpSelectorNotaLat,originalBook);
				TA.addMouseOverHandler(ActivetMouse);

				TA.addMouseOutHandler(new MouseOutHandler() {
					
					@Override
					public void onMouseOut(MouseOutEvent arg0) {

						for (SelectorPanel SP : popUpSelectorNotaLat) {
							SP.hide();
							
						}
						
					}
				});
				DecoratorPanel DP= new DecoratorPanel();
				DP.add(TA);
				PanelInserActAnota.add(DP);
			}
//				PanelInserActAnota.add(new Label(anotaciones.getElementAsoc().getValue()));
		}
		
	}
	
	protected boolean PermisoEscritura() {
		return true;
	}


	public void Promedia_coor(ElementTempCoordenadasAnotacion elementTempCoordenadasAnotacion) {

		int Hi = originalBook.getHeight();
		int Wi = originalBook.getWidth();
		
		Long Xp = elementTempCoordenadasAnotacion.getX();
		
		Long X0 =((Xp*1000)/Wi);
		
		Long Yp = elementTempCoordenadasAnotacion.getY();
		
		Long Y0 =((Yp*1000)/Hi);
		
		Long XptempW = elementTempCoordenadasAnotacion.getX()+elementTempCoordenadasAnotacion.getWidth();
		
		Long X0tempW =((XptempW*1000)/Wi);
		
		Long W0= X0tempW-X0;
		
		Long YptempH = elementTempCoordenadasAnotacion.getY()+elementTempCoordenadasAnotacion.getHeight();
		
		Long Y0tempH =((YptempH*1000)/Hi);
		
		Long H0= Y0tempH-Y0;
		
		elementTempCoordenadasAnotacion.setX(X0);
		elementTempCoordenadasAnotacion.setY(Y0);
		elementTempCoordenadasAnotacion.setWidth(W0);
		elementTempCoordenadasAnotacion.setHeight(H0);
		
		
		
	}



	public boolean isWaitingUpdate() {
		return WaitingUpdate;
	}





	public void setWaitingUpdate(boolean update) {
		if (TC!=null&&!update)
		{
			
			Documento=getVariableBase(RandomIdVars);
			
			GWT.log(Documento.getDocumento().getId()+"");
			
			
			
			StructureJSON S=null;
			for (GrammarJSON gramarpos : Documento.getGramatica()) {
				S=gotContext(gramarpos.getListaS(),ContextId);
				
				if (S!=null)
					{
					
				
				if (!S.getClaseOf().equals(ContextId))
					{
					S=gotContext(gramarpos.getListaS(),S.getClaseOf());				
					ContextId=S.getClaseOf();
					ContextId=S.getClaseOf();
					}
				
				
				
					break;
					}
				
			}
				
				
				HashData=new HashMap<StructureJSON, HashMap<StructureJSON,HashMap<StructureJSON,HashMap<String, StructureJSON>>>>();

				
				List<StructureJSON> S2 = null;
				for (GrammarJSON gramarpos : Documento.getGramatica()) {
					S2=gotMultivaluedContext(gramarpos.getListaS(),ContextId);
					if (S2!=null)
						break;
				}
				
				if (S2==null)
					{
					setError(ERROR_STRUCTURE);
					return;
					}
				
				for (StructureJSON punto : S2)
					procesaPoint(punto);
				
				

			
			
			HashMap<StructureJSON, HashMap<StructureJSON, HashMap<String, StructureJSON>>> TablaHoja = HashData.get(EE);
			for (Entry<StructureJSON, HashMap<StructureJSON, HashMap<String, StructureJSON>>> elementoClave : TablaHoja.entrySet()) {
				if (elementoClave.getKey().getValue()!=null&&elementoClave.getKey().getValue().isEmpty())
				{
					int necesario = TC.getTextSelector().size();
					int actual= elementoClave.getValue().keySet().size();
					if (necesario>actual)
						{
						
							createIterator(Long.toString(elementoClave.getValue().entrySet().iterator().next().getKey().getId().get(0)),Long.toString(Documento.getDocumento().getId()),"anote2",false);

//							ControladorEditor.createIteration(Long.toString(elementoClave.getValue().entrySet().iterator().next().getKey().getId().get(0)),Long.toString(Documento.getDocumento().getId()),"anote",false);
							break;
						}
					else
					{
						TC.setLista(elementoClave.getKey(),elementoClave.getValue());					
						WaitingUpdate=update;
						break;
					}
				}
			}
			
			
				
			
			
		}
		else
			WaitingUpdate=update;
		
	}

	private static native String createIterator(String contextId, String documentId, String Editor, boolean isgrammar) /*-{
	return window.parent.createIterator(contextId,documentId,Editor,isgrammar);
	}-*/;
	
	
	private JSONObject setVariableBase2(DocumentCompleteJSON documento2, String randomIdVars2) {
		JSONObject DocumentoJ = CreateJSONObject.create(documento2);
//		JSONObject DocumentoJ = CreateJSONObject(documento2);
		setVariableBase3(DocumentoJ.toString(),randomIdVars2);
		return DocumentoJ;
		
	}
	
	public static native void setVariableBase3(String DocumentoExpandido, String idrandomdoct) /*-{


	$wnd.tmp=DocumentoExpandido;
	$wnd.str = '$wnd.JSDocExpand'+idrandomdoct +' = $wnd.tmp';
	console.log($wnd.str);
	eval($wnd.str)

	}-*/;
	
	
	
	public static native String getVariableBaseJSONOBJS(String documentID2) /*-{
	$wnd.daletmp = '$wnd.dale = $wnd.JSDocExpand'+documentID2;
	eval($wnd.daletmp)
	return  $wnd.dale;	  

}-*/;
	
}
