/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription.utils;

import com.liveperson.api.ams.cm.types.ParticipantRole;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author eitanya
 */
public class GeneralUtils {

    public static AtomicInteger ai = new AtomicInteger(10000);

    public interface RunWithEx {

        void run() throws Exception;
    }

    public static void ignoreExceptions(RunWithEx ex) {
        try {
            ex.run();
        } catch (Exception ex1) {
            throw new RuntimeException(ex1);
        }
    }

    public static void reflectPrivateFields(String prefix, String fieldName, Object object) {
        try {
            Field[] fs = object.getClass().getDeclaredFields();
            for (Field f : fs) {
                String name = f.getName();
                f.setAccessible(true);
                Object o = f.get(object);
                if (name.equals(fieldName)) {
                    System.out.println("### "+ prefix + " reflect ###   id = " + o);
                    return;
                }

                if (!name.equals(fieldName)) {
                    reflectPrivateFields(prefix, fieldName, o);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(String s){
        return s == null || s.isEmpty();
    }


    /**
     * get the user role BY specific userID
     *
     * @param userId       id of the desired user
     * @return Participant Role of the wanted userId. It return Optional since user may not be in the map
     */
    public static Optional<ParticipantRole> getUserRole(String userId, Map<ParticipantRole, Collection<String>> participants) {

        return participants.entrySet().stream()
                .filter(e -> e.getValue().contains(userId))
                .findFirst()
                .map(Map.Entry::getKey);
    }
}
