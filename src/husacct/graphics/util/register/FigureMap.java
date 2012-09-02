package husacct.graphics.util.register;

import husacct.common.dto.AbstractDTO;
import husacct.graphics.presentation.figures.BaseFigure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FigureMap {
	protected HashMap<BaseFigure, AbstractDTO> figureDTOMap;

	public FigureMap() {
		figureDTOMap = new HashMap<BaseFigure, AbstractDTO>();
	}

	public void addFigure(BaseFigure figure, AbstractDTO dto) {
		figureDTOMap.put(figure, dto);
	}

	public boolean containsFigure(BaseFigure figure) {
		return figureDTOMap.containsKey(figure);
	}

	public AbstractDTO getFigureDTO(BaseFigure figure) {
		return figureDTOMap.get(figure);
	}

	public ArrayList<BaseFigure> getFigures() {
		ArrayList<BaseFigure> figures = new ArrayList<BaseFigure>(figureDTOMap.keySet());
		Collections.sort(figures);
		return figures;
	}
}
