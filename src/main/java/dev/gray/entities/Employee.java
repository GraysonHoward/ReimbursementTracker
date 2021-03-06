package dev.gray.entities;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * Class designed to hold an entry from the Employee table
 * from the receipt database
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id = 0;
    private String fName = null;
    private String lName = null;
}