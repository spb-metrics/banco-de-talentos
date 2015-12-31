/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 C�mara dos Deputados, Brasil
 * 
 * Este arquivo � parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS � um software livre; voc� pode redistribu�-lo e/ou modific�-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a.
 *
 * Este programa � distribu�do na esperan�a que possa ser �til, mas SEM NENHUMA GARANTIA; sem uma garantia impl�cita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

package br.gov.camara.biblioteca.util;

//GenericComparator.java
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * @author Gervase Gallant gervasegallant@yahoo.com
 */
public class GenericComparator implements Comparator {
    public static final String ASC = "asc";

    public static final String DESC = "desc";

    private String fieldName = null;

    private String compareField = null;

    private String compareMethod = null;

    private String sortOrder = null;

    /**
     * Constructor for GenericComparator.
     */
    public GenericComparator(String field, String order, String compareField, String compareMethod) {
        super();
        this.fieldName = field;
        this.compareField = compareField;
        this.compareMethod = compareMethod;
        if (order == null || order.equals(ASC)) {
            this.sortOrder = ASC;
        } else if (order.equals(DESC)) {
            this.sortOrder = DESC;
        }

    }

    public GenericComparator(String field) {
        this(field, "asc", null, null);
    }

    /**
     * @see java.util.Comparator#compare(Object, Object)
     */
    public int compare(Object o1, Object o2) {
        Object value1;
        Object value2;
        try {
            Field f = this.getAccessibleField(o1);

            if (f == null)
                return 0;

            if (this.sortOrder.equals(ASC)) {
                value1 = f.get(o1);
                value2 = f.get(o2);
            } else {
                value2 = f.get(o1);
                value1 = f.get(o2);

            }

            if (this.compareField != null) { // someone told us which field
                // to
                // sort on...
                f = this.getAccessibleField(this.compareField);

                value1 = f.get(value1);
                value2 = f.get(value2);
                return this.compareField(value1, value2);

            } else if (this.compareMethod != null) {
                Method m = value1.getClass().getMethod(this.compareMethod, null); // assume
                // public....
                value1 = m.invoke(value1, null);
                value2 = m.invoke(value2, null);
                return this.compareField(value1, value2);
            }

            return this.compareField(value1, value2);
        } catch (Exception nsfe) {
            nsfe.printStackTrace();
            return 0;
        }

    }

    private int compareField(Object value1, Object value2) throws IllegalAccessException {

        if (value1 instanceof Comparable) {
            return ((Comparable) value1).compareTo(value2);

        } else {
            return value1.toString().compareTo(value2.toString()); // try
            // String
            // sort.
        }

    }

    private Field getAccessibleField(Object object) {

        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(this.fieldName)) {
                fields[i].setAccessible(true); // hope Security Manager
                // will allow;
                return fields[i];

            }

        }
        System.out.println("Sorry...couldn't sort on " + this.fieldName);

        return null;
    }

}
