package co.bugg.quickplay.gui;

import co.bugg.quickplay.Game;
import co.bugg.quickplay.Icons;
import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.gui.button.ArrowButton;
import co.bugg.quickplay.gui.button.GameButton;
import co.bugg.quickplay.util.GlUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class MainGui extends GuiScreen {

    /**
     * When all the buttons don't fit on the page, multiple
     * pages have to be made with back and forward buttons
     * on the bottom. This number is for what page this GUI is.
     */
    int pageNumber = 1;

    public MainGui() {
        super();
    }

    public MainGui(int pageNumber) {
        super();
        this.pageNumber = pageNumber;
    }

    /**
     * How far apart each icon should be
     */
    int iconXSpacing = 5;
    int iconYSpacing = 15;

    /**
     * Margins off of edges for where
     * buttons can be drawn
     */
    int marginTop = 5;
    int marginBottom = 25;
    int marginSides = 30;

    /**
     * Total number of icons
     */
    int iconCount = Icons.list.size();
    /**
     * How many icons can fit on one page
     * Set in initGui()
     */
    int iconsPerPage;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Draw the credits
        drawString(fontRendererObj, QuickPlay.credit, width / 2 - fontRendererObj.getStringWidth(QuickPlay.credit) / 2, height - 10, QuickPlay.configManager.getConfig().colors.get("primary").getRGB());
        GlUtil.resetGlColor();
    }

    /**
     * Button ID of the back button
     */
    int backButtonId;
    /**
     * Whether a back button currently is drawn on the screen
     */
    boolean backButtonExists = false;
    /**
     * Button ID of the forward button
     */
    int forwardButtonId;
    /**
     * Whether a forward button currently is drawn on the screen
     */
    boolean forwardButtonExists = false;

    @Override
    public void initGui() {

        // A grid of how many columns and rows are necessary
        HashMap<String, Integer> grid = getGrid();

        // Clear the list of buttons
        buttonList.clear();

        // Initially set the button ID
        int buttonId = 0;

        // How many icons can fit on the page
        iconsPerPage = grid.get("columns") * grid.get("rowsPerPage");

        // Draw back and forward buttons
        if(grid.get("pages") != null) {
            // If we aren't on page 1
            if(pageNumber > 1) {
                ArrowButton back = new ArrowButton(buttonId++, iconXSpacing, height - ArrowButton.height - iconXSpacing, 0);
                backButtonId = back.id;
                backButtonExists = true;
                buttonList.add(back);
            } else {
                backButtonExists = false;
            }

            // If there's more pages ahead
            if(pageNumber < grid.get("pages")) {
                ArrowButton forward = new ArrowButton(buttonId++, width - ArrowButton.width - iconXSpacing, height - ArrowButton.height - iconXSpacing, 1);
                forwardButtonId = forward.id;
                forwardButtonExists = true;
                buttonList.add(forward);
            } else {
                forwardButtonExists = false;
            }
        }

        // Incremented as each button is drawn
        int currentColumn = 1;
        int currentRow = 1;

        // Indexes for the sub list of games that
        // will be drawn on this page
        int sublistStartingIndex = iconsPerPage * (pageNumber - 1);
        int sublistEndingIndex = sublistStartingIndex + iconsPerPage;
        // If out of bounds, then just go to the end of the list
        if(sublistEndingIndex > iconCount) sublistEndingIndex = iconCount;

        // Loop through the sublist of games
        for(Game entry : new LinkedList<>(Icons.list.subList(sublistStartingIndex, sublistEndingIndex))) {
            // A map of the x, y position to put the button at
            HashMap<String, Integer> pos = getPos(currentColumn, currentRow, grid);

            buttonList.add(entry.getButton(buttonId, pos.get("x"), pos.get("y")));
            buttonId++;

            currentColumn++;
            // If the column is falling out of bounds
            if(currentColumn > grid.get("columns")) {
                currentRow++;
                currentColumn = 1;

                // If icons are now falling off the page then stop drawing
                if(getPos(currentColumn, currentRow, grid).get("y") + Icons.iconHeight > height - marginTop - marginBottom) break;
            }
        }

        super.initGui();
    }

    /**
     * Get grid information, such as how many
     * columns can fit per page, how many total
     * rows there are, and how many pages
     * are needed to fit all the icons within
     * the margins.
     * @return HashMap containing grid information
     */
    public HashMap<String, Integer> getGrid() {
        // HashMap containing how many columns and
        // rows there are
        HashMap<String, Integer> grid = new HashMap<>();

        // True width/height is the width/height of the screen with the proper margins
        int trueWidth = width - marginSides * 2;
        int trueHeight = height - marginTop - marginBottom;

        // First calculate how many columns there will be
        {
            int iconWidthWithSpacing = Icons.iconWidth + iconXSpacing;
            int columnCount = trueWidth / iconWidthWithSpacing;

            // Total number of columns that can fit on the screen
            grid.put("columns", columnCount);

        }

        // Now calculate how many rows there will be
        {
            int rowCount = (int) Math.ceil((double) iconCount / (double) grid.get("columns"));
            grid.put("rows", rowCount);
        }

        // Now calculate how many pages there will be
        {
            int iconHeightWithSpacing = Icons.iconHeight + iconYSpacing;

            int rowCountPerPage = trueHeight / iconHeightWithSpacing;
            int pageCount = (int) Math.ceil((double) grid.get("rows") / (double) rowCountPerPage);

            grid.put("pages", pageCount);
            grid.put("rowsPerPage", rowCountPerPage);
        }

        return grid;
    }

    /**
     * Get the position of a single icon, provided
     * the column and row of the icon
     * @param column Column the icon is in
     * @param row Row the icon is in
     * @param grid Grid containing the column & row count
     * @return HashMap with the x and y
     */
    public HashMap<String, Integer> getPos(int column, int row, HashMap<String, Integer> grid) {
        // Hashmap containing the x, y of the icon according to current column and row passed
        HashMap<String, Integer> coords = new HashMap<>();


        // x and y will always start with the margins
        // then increased depending on how many icons
        // between starting margin & passed position.
        // X also has extra margin space, see getUnusedSpace()
        //
        // row is multiplied by pageNumber in getIconCountInRow()
        // because the row needs to be the overall row, and not
        // the row on this page.
        int x = marginSides + getUnusedSpace(getIconCountInRow(row * pageNumber, grid), grid.get("columns")) / 2;
        int y = marginTop;

        {
            int columnWidth = Icons.iconWidth + iconXSpacing;
            x += columnWidth * (column - 1);
        }
        {
            int rowHeight = Icons.iconHeight + iconYSpacing;
            y += rowHeight * (row - 1);
        }

        coords.put("x", x);
        coords.put("y", y);

        return coords;
    }

    /**
     * Get how much space isn't used in columns in pixels
     * Used for centering icons in the GUI
     * @return int of number of pixels that aren't filled
     */
    public int getUnusedSpace(int columnCount, int totalColumns) {
        int trueWidth = width - marginSides * 2;
        int trueIconWidth = Icons.iconWidth + iconXSpacing;
        return trueWidth % trueIconWidth + ((totalColumns - columnCount) * trueIconWidth);
    }

    /**
     * Get the number of icons in the row provided
     * @param row int of what row you're checking
     * @param grid Grid, contains the total number of rows
     * @return int of how many icons are in the row
     */
    public int getIconCountInRow(int row, HashMap<String, Integer> grid) {
        // If not on the last row
        if(grid.get("rows") > row) {
            return grid.get("columns");
        } else {
            int iconModulus = iconCount % grid.get("columns");
            return (iconModulus == 0) ? iconCount : iconModulus;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        // Back button pressed
        if(backButtonExists && button.id == backButtonId) {
            Minecraft.getMinecraft().displayGuiScreen(new MainGui(--pageNumber));

        // forward button pressed
        } else if(forwardButtonExists && button.id == forwardButtonId) {
            Minecraft.getMinecraft().displayGuiScreen(new MainGui(++pageNumber));

        // (presumably) normal game button pressed
        } else {
            // Cast to GameButton so we can use getGame()
            GameButton gameButton = (GameButton) button;
            // Open a new GameGui for the game corresponding to the button clicked
            Minecraft.getMinecraft().displayGuiScreen(new GameGui(gameButton.getGame(), pageNumber));
        }

        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // Close the GUI whenever a key is pressed.
        closeGui();

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Sets the open GUI to null, which is the equivalent of closing the GUI.
     */
    public static void closeGui() {
        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        // Whenever the window is resized, go back to
        // page one to avoid issues with out of bounds
        pageNumber = 1;
        super.onResize(mcIn, w, h);
    }
}
