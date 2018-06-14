/**
 * 
 */
package ucm.fdi.ilsa.client;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class CompositeDocumentDescriptionAnote extends CompositeDocumentEditionAnote{


	

	public CompositeDocumentDescriptionAnote(String randomIdVars, Long contextId, int Height, boolean CompleteView,boolean Grammar, boolean views2) {
		
		super(randomIdVars,contextId,Height,Grammar);

		
	}

	

	@Override
	protected void AddListeners() {
		// TODO No meto los oyentes
		
	}


	@Override
	protected boolean PermisoEscritura() {
		return false;
	}
	
	
}
