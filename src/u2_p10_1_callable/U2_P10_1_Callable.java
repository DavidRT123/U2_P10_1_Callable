/*
 * Crea un programa Java, llámalo U2_P10_1_Callable para ejecutar desde Java los
 * procedimientos de subida salarial que has creado, hazlo para que se pueda ver que el
 * procedimiento funciona.
 */
package u2_p10_1_callable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mdfda
 */
public class U2_P10_1_Callable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String procedimiento = "", sql = "SELECT EMAIL, DEPT_NO, SALARIO FROM PROFESORES";
        int respu, dept, porce = 0;
        ResultSet result;
        Statement s;
        Scanner sc = new Scanner(System.in);
        Boolean sw = false;

        //Filtro para evitar selección incorrecta
        do {
            if (sw) {
                System.err.println("INTRODUCE UN NÚMERO DE 1 A 4");
            }
            System.out.println("Selecciona el procedimiento a ejecutar: ");
            System.out.println("1. subida_1000()");
            System.out.println("2. subida_10()");
            System.out.println("3. subida_por(porcen)");
            System.out.println("4. subida_pordep(porcen, dept)");
            System.out.println("*****************************************");
            respu = sc.nextInt();
            sw = true;
        } while (respu != 1 && respu != 2 && respu != 3 && respu != 4);

        switch (respu) {
            case 1:
                procedimiento = "subida_1000()";
                break;
            case 2:
                procedimiento = "subida_10()";
                break;
            case 3:
                System.out.println("---> Introduce porcentaje: ");
                porce = sc.nextInt();
                procedimiento = "subida_por(" + porce + ")";
                break;
            case 4:
                System.out.println("---> Introduce porcentaje: ");
                porce = sc.nextInt();
                System.out.println("---> Introduce departamento: ");
                dept = sc.nextInt();
                procedimiento = "subida_pordep(" + porce + ", " + dept + ")";
                break;
        }

        procedimiento = "CALL " + procedimiento;

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            Connection con = DriverManager.getConnection("jdbc:hsqldb:C://hsqldb-2.4.1//hsqldb//hsqldb//ejemplo1", "SA", "");

            CallableStatement cS = con.prepareCall(procedimiento);

            //Ejecutar procedimiento
            cS.executeUpdate();

            s = con.createStatement();
            result = s.executeQuery(sql);

            while (result.next()) {
                System.out.println("NOMBRE: " + result.getString("EMAIL") + " || DEPARTAMENTO: " + result.getInt("DEPT_NO") + " || SALARIO: " + result.getFloat("SALARIO"));
            }

            //Cerramos todo
            result.close();
            cS.close();
            s.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(U2_P10_1_Callable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Código de error: " + ex.getErrorCode());
            System.out.println("Mensaje: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
        }

    }

}
