/*
 iVision presentation software
 Copyright (c) 2023 Benjamin Wagner <bwagner@treptowkolleg.de>

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 3.0 of the License, or (at your option) any later version.

 This software is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this software.
*/

package tk.ivision;

import tk.ivision.core.view.View;
import javax.swing.*;

public class Main {
    public static void main(String[] argv) {

        SwingUtilities.invokeLater(View::new);

    }
}