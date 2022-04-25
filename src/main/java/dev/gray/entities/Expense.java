package dev.gray.entities;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * Class designed to hold an entry from the Expense table
 * from the receipt database
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private int expId = 0;
    private int emplId = 0;
    private double amount = -1;
    private Status stat = null;
}