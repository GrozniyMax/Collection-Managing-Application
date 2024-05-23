package com.example.demo.CommonClasses.Utils;


import com.example.demo.CommonClasses.Exceptions.DeserializationException;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Утилиты для работы с датаграммами
 */
public class IOUtils {

    static final int PACKET_SIZE=2048;

    public record Chunck(int number,byte[] data,int length) implements Serializable{
        public Chunck(int number, byte[] data, int length) {
            if (data.length>2048) throw new IllegalArgumentException();
            this.number = number;
            this.data = data;
            this.length = length;
        }
    }








    public static class ChunksBuilder{
        private int lenght=0;
        private Map<Integer,byte[]> chunks= new HashMap();

        public void add(byte [] serializedChunk) throws DeserializationException {
            Chunck deserializedChunk = fromByteArray(serializedChunk);
            if (lenght==0) lenght=deserializedChunk.length;
            if (chunks.containsKey(deserializedChunk.number)) throw new DeserializationException("Получен дублирующий пакет");
            chunks.put(deserializedChunk.number,deserializedChunk.data);
        }

        public Object toObject() throws DeserializationException {
            if(!ready()) throw new DeserializationException("Не все пакеты получены");

            byte[] bytes = new byte[2048*lenght];
            int lastIndex = 0;
            for (int i = 0; i < lenght; i++) {
                if (!chunks.containsKey(i)) throw new DeserializationException("Пропущен пакет данных");
                byte[] currentChunk = chunks.get(i);
                for (int j = 0; j < currentChunk.length; j++) {
                    bytes[lastIndex++]=currentChunk[j];
                }
            }
            return fromByteArray(bytes);
        }

        public boolean ready(){
            if (!chunks.containsKey(lenght-1)) return false;
            for (int i = 0; i < lenght; i++) {
                if (!chunks.containsKey(i)) return false;
            }
            return  true;
        }

        public Class getObjClass() throws DeserializationException {
            return toObject().getClass();
        }
    }



    /**
     * Преобразует объект в массив байт
     * @param object объект
     * @return массив байт
     */
    public static byte[] toByteArray(Object object){
        try(var array = new ByteArrayOutputStream()) {
            new ObjectOutputStream(array).writeObject(object);
            return array.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Преобразует массив байт в объект
     * @param array массив байт
     * @param <T> тип объекта
     * @return объект
     */
    public static <T> T fromByteArray(byte[] array){
        try(var reader = new ObjectInputStream(new ByteArrayInputStream(array));) {
            return (T) reader.readObject();
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Chunck[] getChunks(Serializable object){
        byte[] serialized = toByteArray(object);
        int len = serialized.length%2048==0? serialized.length/2048: serialized.length/2048+1;
        len = serialized.length>2048? len :1;
        Chunck[] chunks = new Chunck[len];
        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = new Chunck(i,Arrays.copyOfRange(serialized,i*2048,(i+1)*2048),chunks.length);
        }
        return chunks;
    }



}
