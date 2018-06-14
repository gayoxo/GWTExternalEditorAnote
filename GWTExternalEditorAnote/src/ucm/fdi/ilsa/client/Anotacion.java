package ucm.fdi.ilsa.client;

public class Anotacion {

	public static final String ANOTATION = "anotation";
	public static final String ANCHOR = "anchor";
	public static final String ANOTATION_TEXT = "text";
	public static final String POSITION = "position";
	public static final String LEFT = "left";
	public static final String TOP="top";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	
	
	
	
//	public static boolean is(ArrayList<OperationalView> shows, String compara) {
//		for (OperationalView operationalView : shows) 
//			for (OperationalValueType iterable_element : operationalView.getValues()) 
//				if (operationalView.getName().toLowerCase().equals(ConstantsValues.CLAVY)&&iterable_element.getName().toLowerCase().equals(ConstantsValues.EDITOR))
//					return iterable_element.getDefault().toLowerCase().equals(compara);
//		
//		return false;
//	}
//
//
//
//
//	public static HashMap<Integer, HashMap<String,CompositeDocumentEditionTabStructure>> isAnchorCorrect(CompositeDocumentEditionTabStructure padre) {
//		
//		HashMap<Integer, HashMap<String,CompositeDocumentEditionTabStructure>> Tabla=new HashMap<Integer, HashMap<String,CompositeDocumentEditionTabStructure>>();
//
//		//los de los iteradores
//		List<InterfaceCompositeDocumentEditionTab> hijos_indirectos=new ArrayList<InterfaceCompositeDocumentEditionTab>();
//		
//		for (InterfaceCompositeDocumentEditionTab hijo : padre.getTabEditHijos())
//		{
//			Structure ElementoHijo = hijo.getElementType();
//			if (hijo instanceof CompositeDocumentEditionTabStructure && ElementoHijo instanceof ElementType && Anotacion.is(((ElementType) ElementoHijo).getShows(),Anotacion.ANCHOR))
//				{
//				HashMap<String, CompositeDocumentEditionTabStructure> TablaTemp=Anotacion.isPositionCorrect((CompositeDocumentEditionTabStructure) hijo);
//				if (TablaTemp.size()==4)
//					{
//					
//					Integer AmbitoAct=0;
//					if (hijo.getAmbitos().size()>0)	
//						AmbitoAct=hijo.getAmbitos().get(hijo.getAmbitos().size()-1);
//
//					HashMap<String, CompositeDocumentEditionTabStructure> resulttab = Tabla.get(AmbitoAct);
//					if (resulttab==null)
//						resulttab=new HashMap<String, CompositeDocumentEditionTabStructure>();
//					
//					resulttab.put(Anotacion.ANCHOR, (CompositeDocumentEditionTabStructure) hijo);				
//					
//					for (Entry<String, CompositeDocumentEditionTabStructure> hijos : TablaTemp.entrySet())
//						resulttab.put(hijos.getKey(), hijos.getValue());
//
//					Tabla.put(AmbitoAct, resulttab);
////					Window.alert(Anotacion.POSITION + " Found");
//					}
//				}else
//					if (hijo instanceof CompositeDocumentEditionTabStructure && ElementoHijo instanceof Iterator)
//						hijos_indirectos.addAll(((CompositeDocumentEditionTabStructure)hijo).getTabEditHijos());
//
//		}
//		
//		
//		while (!hijos_indirectos.isEmpty())
//		{
//			InterfaceCompositeDocumentEditionTab hijo = hijos_indirectos.remove(0);
//			Structure ElementoHijo = hijo.getElementType();
//			if (hijo instanceof CompositeDocumentEditionTabStructure && ElementoHijo instanceof ElementType && Anotacion.is(((ElementType) ElementoHijo).getShows(),Anotacion.ANCHOR))
//				{
//				HashMap<String, CompositeDocumentEditionTabStructure> TablaTemp=Anotacion.isPositionCorrect((CompositeDocumentEditionTabStructure) hijo);
//				if (TablaTemp.size()==4)
//					{
//					
//					Integer AmbitoAct=0;
//					if (hijo.getAmbitos().size()>0)	
//						AmbitoAct=hijo.getAmbitos().get(hijo.getAmbitos().size()-1);
//
//					HashMap<String, CompositeDocumentEditionTabStructure> resulttab = Tabla.get(AmbitoAct);
//					if (resulttab==null)
//						resulttab=new HashMap<String, CompositeDocumentEditionTabStructure>();
//					
//					resulttab.put(Anotacion.ANCHOR, (CompositeDocumentEditionTabStructure) hijo);				
//					
//					for (Entry<String, CompositeDocumentEditionTabStructure> hijos : TablaTemp.entrySet())
//						resulttab.put(hijos.getKey(), hijos.getValue());
//
//					Tabla.put(AmbitoAct, resulttab);
////					Window.alert(Anotacion.POSITION + " Found");
//					}
//				}
//			else
//				if (hijo instanceof CompositeDocumentEditionTabStructure && ElementoHijo instanceof Iterator)
//					hijos_indirectos.addAll(((CompositeDocumentEditionTabStructure)hijo).getTabEditHijos());
//		}
//	
//		return Tabla;
//	}
//
//
//
//
//	private static HashMap<String, CompositeDocumentEditionTabStructure> isPositionCorrect(CompositeDocumentEditionTabStructure padre) {
//		HashMap<String, CompositeDocumentEditionTabStructure> Tabla=new HashMap<String, CompositeDocumentEditionTabStructure>();
//		HashSet<CompositeDocumentEditionTabStructure> Lista=new HashSet<CompositeDocumentEditionTabStructure>();
//		
//		for (InterfaceCompositeDocumentEditionTab hijo : padre.getTabEditHijos())
//		{
//			Structure ElementoHijo = hijo.getElementType();
//			if (hijo instanceof CompositeDocumentEditionTabStructure && ElementoHijo instanceof TextElementType && Anotacion.is(((ElementType) ElementoHijo).getShows(),Anotacion.TOP))
//				{
//					Tabla.put(Anotacion.TOP, (CompositeDocumentEditionTabStructure) hijo);
//					Lista.add((CompositeDocumentEditionTabStructure) hijo);
////					Window.alert(Anotacion.TOP + " Found");
//				}
//			if (hijo instanceof CompositeDocumentEditionTabStructure && ElementoHijo instanceof TextElementType && Anotacion.is(((ElementType) ElementoHijo).getShows(),Anotacion.LEFT))
//			{
//				Tabla.put(Anotacion.LEFT, (CompositeDocumentEditionTabStructure) hijo);
//				Lista.add((CompositeDocumentEditionTabStructure) hijo);
////				Window.alert(Anotacion.BOTTON + " Found");
//			}
//			
//			if (hijo instanceof CompositeDocumentEditionTabStructure && ElementoHijo instanceof TextElementType && Anotacion.is(((ElementType) ElementoHijo).getShows(),Anotacion.WIDTH))
//			{
//				Tabla.put(Anotacion.WIDTH, (CompositeDocumentEditionTabStructure) hijo);
//				Lista.add((CompositeDocumentEditionTabStructure) hijo);
////				Window.alert(Anotacion.TOP + " Found");
//			}
//		if (hijo instanceof CompositeDocumentEditionTabStructure && ElementoHijo instanceof TextElementType && Anotacion.is(((ElementType) ElementoHijo).getShows(),Anotacion.HEIGHT))
//		{
//			Tabla.put(Anotacion.HEIGHT, (CompositeDocumentEditionTabStructure) hijo);
//			Lista.add((CompositeDocumentEditionTabStructure) hijo);
////			Window.alert(Anotacion.BOTTON + " Found");
//		}
//
//		}
//	
//		return Tabla;
//	}

}
