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

/**
 * $Id: BeanSorter.java,v 1.1.1.1 2003/02/04 01:35:34 epesh Exp $
 *
 * Status: Under Development
 *
 * Todo
 *   - implementation
 *   - documentation (javadoc, examples, etc...)
 *   - junit test cases
 **/
 
package sigesp.comum.util.biblioteca;

import java.util.Comparator;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * This utility class is used to sort the list that the table is viewing
 * based on arbitrary properties of objects contained in that list.  The
 * only assumption made is that the object returned by the property is
 * either a native type, or implements the Comparable interface.
 *
 * If the property does not implement Comparable, then this little sorter
 * object will just assume that all objects are the same, and quietly do
 * nothing (so you should check for Comparable objecttypes elsewhere as
 * this object won't complain).
 **/

public class OrdenadorBean extends Object implements Comparator
{
   private String property;


   /**
    * BeanSorter is a decorator of sorts, you need to initialize it with
    * the property name of the object that is to be sorted (getXXX method
    * name).  This property should return a Comparable object.
    */

   public OrdenadorBean(String property)
   {
      this.property = property;
   }


   /**
    * Compares two objects by first fetching a property from each object
    * and then comparing that value.  If there are any errors produced while
    * trying to compare these objects then a RunTimeException will be
    * thrown as any error found here will most likely be a programming
    * error that needs to be quickly addressed (like trying to compare
    * objects that are not comparable, or trying to read a property from a
    * bean that is invalid, etc...)
    *
    * @throws RuntimeException if there are any problems making a comparison
    *    of the object properties.
    */

   public int compare( Object o1, Object o2 )
      throws RuntimeException
   {
      if( property == null ) {
         throw new NullPointerException( "Null property provided which " +
            "prevents BeanSorter sort" );
      }

      try {
         Object p1 = null;
         Object p2 = null;

         p1 = PropertyUtils.getProperty( o1, property );
         p2 = PropertyUtils.getProperty( o2, property );

         if( p1 instanceof Comparable && p2 instanceof Comparable ) {
            Comparable c1 = (Comparable)p1;
            Comparable c2 = (Comparable)p2;
            return c1.compareTo( c2 );
         } else {
            throw new RuntimeException( "Object returned by property \"" +
               property + "\" is not a Comparable object" );
         }
      } catch( IllegalAccessException e ) {
         throw new RuntimeException( "IllegalAccessException thrown while " +
            "trying to fetch property \"" + property + "\" during sort" );
      } catch( InvocationTargetException e ) {
         throw new RuntimeException( "InvocationTargetException thrown while " +
            "trying to fetch property \"" + property + "\" during sort" );
      } catch( NoSuchMethodException e ) {
         throw new RuntimeException( "NoSuchMethodException thrown while " +
            "trying to fetch property \"" + property + "\" during sort" );
      }
   }


   /**
    * Is this Comparator the same as another one...
    */

   public boolean equals( Object obj )
   {
      if( obj == this ) return true;

      if( obj instanceof OrdenadorBean ) {
         if( this.property != null ) {
            return this.property.equals( ( (OrdenadorBean)obj ).property );
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
