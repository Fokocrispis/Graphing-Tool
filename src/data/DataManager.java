package data;

import java.util.ArrayList;
import java.util.List;

import graph.CoordinateSystem;

public class DataManager {

	private SimpleData simpleData;
	private List<Node> nodeList = new ArrayList<>();
	private dataSelector dataSelected;
	private int n = 0;
	private String audioFile;
	
	public enum dataSelector{
		SIMPLE,
		AUDIO,
		NODE;
	}
	
	public enum SortType {
		SELECTION,
		INSERTION,
		BUBBLE,
		QUICK;
	}

	public DataManager(dataSelector ds, String audioFile) {
		this.dataSelected = ds;
		switch(ds) {
		case SIMPLE:
			new PairGenerator(n);
			simpleData = new SimpleData();
			break;
		case AUDIO:
			simpleData = new SimpleData(audioFile);
			break;
		case NODE:
			nodeList = VideoGame.videoGameGenerator();
			break;
		default:
			break;
		}	
	}
	
	public DataManager(dataSelector ds, int n) {
		this.dataSelected = ds;
		switch(ds) {
		case SIMPLE:
			new PairGenerator(n);
			simpleData = new SimpleData();
			break;
		case AUDIO:
			simpleData = new SimpleData(audioFile);
			break;
		case NODE:
			nodeList = VideoGame.videoGameGenerator();
			break;
		default:
			break;
		}	
	}
	
	public DataManager(dataSelector ds) {
		this.dataSelected = ds;
		switch(ds) {
		case SIMPLE:
			new PairGenerator(n);
			simpleData = new SimpleData();
			break;
		case AUDIO:
			simpleData = new SimpleData(audioFile);
			break;
		case NODE:
			nodeList = VideoGame.videoGameGenerator();
			break;
		default:
			break;
		}
	}
	
	public dataSelector getDataSelected() {
		return this.dataSelected;
	}

	public SimpleData getSimpleData() {
		return simpleData;
	}
	
	public List<Node> getNodeData(){
		return nodeList;
	}

	public void sortData(CoordinateSystem coordinateSystem) {
//		Sorting.quickSort(simpleData.getData(), 0, 999, coordinateSystem);
		Sorting.reverseSort(simpleData.getData(), coordinateSystem);

	}
	
	public void sortData(CoordinateSystem coordinateSystem, SortType sortType, dataSelector dataType) {
		switch(dataType) {
		case SIMPLE:
			switch (sortType) {
			case SELECTION:
				Sorting.selectionSort(simpleData.getData(), coordinateSystem);
				break;
			case INSERTION:
				Sorting.insertionSort(simpleData.getData(), coordinateSystem);
				break;
			case BUBBLE:
				Sorting.bubbleSort(simpleData.getData(), coordinateSystem);
				break;
			case QUICK:
				Sorting.quickSort(simpleData.getData(), simpleData.get(0).x-1, simpleData.getSize()-1, coordinateSystem);
				break;
			}
			break;

		case NODE:
			switch (sortType) {
			case SELECTION:
				
				break;
			case INSERTION:
				
				break;
			case BUBBLE:
				
				break;
			case QUICK:
				//fill with quicksort class
				break;
			}
			break;
		case AUDIO:
			break;
		default:
			break;
		}
	}

}
