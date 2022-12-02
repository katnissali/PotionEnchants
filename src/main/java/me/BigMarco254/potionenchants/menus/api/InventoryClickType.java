package me.BigMarco254.potionenchants.menus.api;

import org.bukkit.event.inventory.InventoryAction;

public enum InventoryClickType {
    LEFT("LEFT", 0, true, false),
    SHIFT_LEFT("SHIFT_LEFT", 1, true, true),
    RIGHT("RIGHT", 2, false, false),
    OTHER("OTHER", 3, false, false);

    private boolean leftClick;
    private boolean shiftClick;

    InventoryClickType(String s, int n, boolean leftClick, boolean shiftClick) {
        this.leftClick = leftClick;
        this.shiftClick = shiftClick;
    }

    public static InventoryClickType fromInventoryAction(InventoryAction action) {
        switch (action) {
            case PICKUP_ALL:
            case PLACE_ALL:
            case PLACE_SOME:
            case SWAP_WITH_CURSOR: {
                return InventoryClickType.LEFT;
            }
            case PICKUP_HALF:
            case PLACE_ONE: {
                return InventoryClickType.RIGHT;
            }
            case MOVE_TO_OTHER_INVENTORY: {
                return InventoryClickType.SHIFT_LEFT;
            }
            default: {
                return InventoryClickType.OTHER;
            }
        }
    }

    public boolean isLeftClick() {
        return this.leftClick && this != InventoryClickType.OTHER;
    }

    public boolean isRightClick() {
        return !this.leftClick && this != InventoryClickType.OTHER;
    }

    public boolean isShiftClick() {
        return this.shiftClick;
    }
}

