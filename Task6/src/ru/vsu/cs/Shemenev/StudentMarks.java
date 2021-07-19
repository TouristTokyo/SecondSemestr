package ru.vsu.cs.Shemenev;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StudentMarks implements Comparable<StudentMarks>{
    protected Map<String,String> map = new HashMap<>();
    boolean visited = false;

    @Override
    public int compareTo(StudentMarks st) {
        Set<String> subjects = map.keySet();
        int res = 1;
        for(String subject:subjects){
            res = map.get(subject).compareTo(st.map.get(subject));
            if(res!=0){
                return res;
            }
        }
        return res;
    }
}
