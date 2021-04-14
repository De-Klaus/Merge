import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergerSorter {
	
	public <T> List<T> merge(final List<T> src1, final List<T> src2, final Comparator<T> comparator) {
        final List<T> result = new ArrayList<>();
        int index1 = 0;
        int index2 = 0;
        int size = src1.size() + src2.size();
        for (int i = 0; i < size; i++) {
            if(index1 < src1.size()
                    && (index2 >= src2.size()|| comparator.compare(src1.get(index1), src2.get(index2)) < 0)) {
                result.add(src1.get(index1));
                index1++;
            }
            else {
                result.add(src2.get(index2));
                index2++;
            }
        }
        return result;
    }

}
