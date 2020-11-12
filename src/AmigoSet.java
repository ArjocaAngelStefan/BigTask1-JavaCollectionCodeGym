import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet implements Serializable, Cloneable, Set {
    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;


    public AmigoSet(){
        map = new HashMap<>();
    }
    public AmigoSet(Collection<? extends E> collection){
        int cap = (int) Math.max(16, Math.ceil(collection.size()/.75f));

        this.map = new HashMap<>(cap);

        for(E e: collection){
            map.put(e, PRESENT);
        }

    }
    public boolean add(Object e){
        if(map.containsKey(e)){
            return false;
        }else{
            map.put((E) e, PRESENT);
            return true;
        }
    }

    @Override
    public Iterator iterator() {


        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        if(map.isEmpty()){
            return true;
        }else
            return false;
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public Object clone() {

        try{
            AmigoSet clone = (AmigoSet) super.clone();
            clone.map = (HashMap) map.clone();
            return clone;
        }catch (Exception e){
            throw new InternalError(e);
        }
    }
    Set<E> set;
    int cap;
    float loadF;
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {

        cap = HashMapReflectionHelper.callHiddenMethod(map, "capacity");
        loadF = HashMapReflectionHelper.callHiddenMethod(map, "loadFactor");
        set = new HashSet<>();
        set.addAll(map.keySet());
        objectOutputStream.defaultWriteObject();
    }
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        map = new HashMap<>(cap, loadF);
        Iterator<E>iterator = set.iterator();
        while(iterator.hasNext()){
            map.put(iterator.next(), PRESENT);
        }
    }
}
