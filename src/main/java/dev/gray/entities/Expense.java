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
    private int expId;
    private int emplId;
    private double amount;
    private Status stat;
}