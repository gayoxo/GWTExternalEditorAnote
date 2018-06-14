package ucm.fdi.ilsa.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Image;

import fdi.ucm.server.interconect.model.StructureJSON;
import fdi.ucm.server.interconect.model.StructureJSON.TypeOfStructureEnum;

public class MouseOverHandlerAreAnot implements MouseOverHandler {



	private ArrayList<SelectorPanel> UpdatePop;
	private HashMap<StructureJSON, HashMap<String, StructureJSON>> tablaElem;
	private Image originalBook;

	public MouseOverHandlerAreAnot(HashMap<StructureJSON, HashMap<String, StructureJSON>> hashMap, ArrayList<SelectorPanel> popUpSelectorNotaLat, Image originalBook) {
		tablaElem=hashMap;
		UpdatePop=popUpSelectorNotaLat;
		this.originalBook=originalBook;
	}

	@Override
	public void onMouseOver(MouseOverEvent arg0) {
	
		for (SelectorPanel selectorPanel : UpdatePop) {
			selectorPanel.hide();
		}
		
		UpdatePop.clear();
		
		for (Entry<StructureJSON, HashMap<String, StructureJSON>> selectorPanel : tablaElem.entrySet()) {
			SelectorPanel PProc=createPop(selectorPanel.getValue());
			if (PProc!=null)
			{
				PProc.show();
				UpdatePop.add(PProc);
			}
		}

	}

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
				{
				int Hi = originalBook.getHeight();
				int Wi = originalBook.getWidth();
				
				Long Xp = Left;
				
				Long X0 =((Xp*Wi)/1000);
				
				Long Yp = Top;
				
				Long Y0 =((Yp*Hi)/1000);
				
				Long XptempW = Left+Width;
				
				Long X0tempW =((XptempW*Wi)/1000);
				
				Long W0= X0tempW-X0;
				
				Long YptempH = Top+Height;
				
				Long Y0tempH =((YptempH*Hi)/1000);
				
				Long H0= Y0tempH-Y0;
				
				return new SelectorPanel(X0.intValue(), Y0.intValue(), originalBook.getAbsoluteLeft(), originalBook.getAbsoluteTop(), W0.intValue(), H0.intValue());
				}
			else
				return null;
		} catch (Exception e) {
			return null;
		}
		
	
		
	}

}
