/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 Câmara dos Deputados, Brasil
 * 
 * Este arquivo é parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); na versão 2 da Licença.
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título "LICENCA.txt", junto com este programa, se não, escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
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
