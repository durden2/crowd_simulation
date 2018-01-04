/**
 * Created by Gandi on 25/11/2017.
 */
public class Heap {

    Element[] items;
    int currentItemCount;

	public Heap() {
	    int maxHeapSize = Constants.mapHeight * Constants.mapWidth + 2;
        items = new Element[maxHeapSize];
    }

    public void Add(Element item) {
        item.heapIndex = currentItemCount;
        items[currentItemCount] = item;
        SortUp(item);
        currentItemCount++;
    }


    void SortUp(Element item) {
        int parentIndex = (item.heapIndex-1)/2;

        while (true) {
            Element parentItem = items[parentIndex];
            if (item.CompareTo(parentItem) > 0) {
                Swap (item, parentItem);
            }
            else {
                break;
            }
            parentIndex = (item.heapIndex-1)/2;
        }
    }

    void SortDown(Element item) {
        while (true) {
            int childIndexLeft = item.heapIndex * 2 + 1;
            int childIndexRight = item.heapIndex * 2 + 2;
            int swapIndex = 0;

            if (childIndexLeft < currentItemCount) {
                swapIndex = childIndexLeft;

                if (childIndexRight < currentItemCount) {
                    if (items[childIndexLeft].CompareTo(items[childIndexRight]) < 0) {
                        swapIndex = childIndexRight;
                    }
                }

                if (item.CompareTo(items[swapIndex]) < 0) {
                    Swap (item,items[swapIndex]);
                }
                else {
                    return;
                }

            }
            else {
                return;
            }

        }
    }


    void Swap(Element itemA, Element itemB) {
	    // swap the items ang heap values
        items[itemA.heapIndex] = itemB;
        items[itemB.heapIndex] = itemA;
        int itemAIndex = itemA.heapIndex;
        itemA.heapIndex = itemB.heapIndex;
        itemB.heapIndex = itemAIndex;
    }

    public Element RemoveFirst() {
        Element firstItem = items[0];
        currentItemCount--;
        items[0] = items[currentItemCount];
        items[0].heapIndex = 0;
        SortDown(items[0]);
        return firstItem;
    }

    public void UpdateItem(Element item) {
        SortUp(item);
    }

    public int Count() {
	    return currentItemCount;
    }

    public boolean Contains(Element item) {
        return item.equals(items[item.heapIndex]);
    }



}

