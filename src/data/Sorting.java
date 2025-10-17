package data;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import graph.CoordinateSystem;

public class Sorting {

	private static long wait = 1;

	static void bubbleSort(List<Point> points, CoordinateSystem cs) {
		new Thread(() -> {

			int n = points.size();
			int i, j, temp;
			boolean swapped;

			for (i = 0; i < n - 1; i++) {
				swapped = false;
				for (j = 0; j < n - i - 1; j++) {
					if (points.get(j).y > points.get(j + 1).y) {

						temp = points.get(j).y;
						points.get(j).y = points.get(j + 1).y;
						points.get(j + 1).y = temp;
						swapped = true;
						cs.pointToSort(points.get(j), Color.red);
						cs.repaintCoordinates();

						try {
							Thread.sleep(wait);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				cs.pointToHighlight(points.get(n - i - 1), Color.green);
				cs.repaintCoordinates();
				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!swapped)
					break;
			}
			cs.fullySorted();
//			reverseSort(points, cs);
		}).start();
	}

	static void insertionSort(List<Point> points, CoordinateSystem cs) {
		new Thread(() -> {
			int n = points.size();
			for (int i = 1; i < n; ++i) {
				int key = points.get(i).y;
				int j = i - 1;

				while (j >= 0 && points.get(j).y > key) {
					points.get(j + 1).y = points.get(j).y;
					j = j - 1;

					cs.pointToSort(points.get(j + 1), Color.red);
					cs.repaintCoordinates();

					try {
						Thread.sleep(wait);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				points.get(j + 1).y = key;
				if (i < n - 2)
					cs.pointToHighlight(points.get(i + 1), Color.green);
				cs.repaintCoordinates();

				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			cs.fullySorted();
//			reverseSort(points, cs);
		}).start();
	}
	
	static void selectionSort(List<Point> points, CoordinateSystem cs) {
		new Thread(() -> {
			int n = points.size();
			for (int i = 0; i < n - 1; i++) {
				int min = i;

				for (int j = i + 1; j < n; j++) {
					if (points.get(j).y < points.get(min).y) {
						min = j;

						cs.pointToSort(points.get(min), Color.red);
						cs.repaintCoordinates();

						try {
							Thread.sleep(wait*10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				int temp = points.get(i).y;

				points.get(i).y = points.get(min).y;
				points.get(min).y = temp;

				cs.pointToSort(points.get(min), Color.red);
				cs.pointToHighlight(points.get(i), Color.green);
				cs.repaintCoordinates();

				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			cs.fullySorted();
			try {
				Thread.sleep(wait*100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			reverseSort(points, cs);
		}).start();
	}
	
static int partition( List<Point> points, int low, int high, CoordinateSystem cs) {

        int pivot = points.get(high).y;

        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            if (points.get(j).y < pivot) {
                i++;
                swap(points, i, j);
                cs.pointToSort(points.get(j), Color.red);
				cs.pointToHighlight(points.get(i), Color.green);
				cs.repaintCoordinates();
            }
        }
        swap(points, i + 1, high);  
        return i + 1;
    }

    static void swap(List<Point> points, int i, int j) {
        int temp = points.get(i).y;
        points.get(i).y = points.get(j).y;
        points.get(j).y = temp;
    }

	static void quickSort(List<Point> points, int low, int high, CoordinateSystem cs) {
		new Thread(() -> {
			if (low < high) {
				int pi = partition(points, low, high, cs);
				
				try {
					Thread.sleep(wait*50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				quickSort(points, low, pi - 1, cs);
				quickSort(points, pi + 1, high, cs);
			}
		}).start();
	}
	
	static void reverseSort(List<Point> points, CoordinateSystem cs) {
		new Thread(() -> {
			int n = points.size();
			for (int i = 0; i < n - 1; i++) {
				int max = i;

				for (int j = i + 1; j < n; j++) {
					if (points.get(j).y > points.get(max).y) {
						max = j;

						cs.pointToSort(points.get(max), Color.red);
						cs.repaintCoordinates();

						try {
							Thread.sleep(wait*1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				int temp = points.get(i).y;

				points.get(i).y = points.get(max).y;
				points.get(max).y = temp;

				cs.pointToSort(points.get(max), Color.red);
				cs.pointToHighlight(points.get(i), Color.green);
				cs.repaintCoordinates();

				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			cs.fullySorted();
			try {
				Thread.sleep(wait*100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			insertionSort(points, cs);
		}).start();
	}
}
