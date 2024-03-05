package io.hyleo.obsb.display;

import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

@Data
@Accessors(fluent = true)
@Builder(builderMethodName = "custom")
@AllArgsConstructor(staticName = "custom")
@EqualsAndHashCode
@ToString
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class Destination implements Cloneable {

	public enum Tag {
		OBJECTIVE_NAME, PREFIX, SUFFIX
	};

	@Default
	DisplaySlot slot = DisplaySlot.SIDEBAR;

	@Default
	@NonNull
	Tag tag = Tag.OBJECTIVE_NAME;

	@Default
	Player player = null;

	@Default
	Integer score = null;

	public static Destination sidebarTitle(DisplaySlot sidebar) {
		return custom(sidebar, Tag.OBJECTIVE_NAME, null, null);

	}

	public static Destination sidebarTitle() {
		return sidebarTitle(DisplaySlot.SIDEBAR);
	}

	public static Destination sidebarLine(DisplaySlot sidebar, int line, boolean suffix) {
		return custom(sidebar, suffix ? Tag.SUFFIX : Tag.PREFIX, null, line);
	}

	public static Destination sidebarLine(int line, boolean suffix) {
		return sidebarLine(DisplaySlot.SIDEBAR, line, suffix);
	}

	public static Destination nametag(Player player, int score, boolean suffix) {
		return custom(null, suffix ? Tag.SUFFIX : Tag.PREFIX, player, score);
	}

	public static Destination nametag(Player player, boolean suffix) {
		return custom(null, suffix ? Tag.SUFFIX : Tag.PREFIX, player, null);
	}

	public static Destination prefix(Player player) {
		return nametag(player, false);
	}

	public static Destination suffix(Player player) {
		return nametag(player, true);
	}

	public static Destination blowname(Player player, int score) {
		return custom(DisplaySlot.BELOW_NAME, Tag.OBJECTIVE_NAME, player, score);
	}

	public boolean sidebarElement() {
		return ScoreboardUtil.isSidebarSlot(slot);
	}

	public boolean isSidebarTitle() {
		return sidebarElement() && isObjective();
	}

	public boolean isSidebarPrefix() {
		return sidebarElement() && tag == Tag.PREFIX;
	}

	public boolean isSidebarSuffix() {
		return sidebarElement() && tag == Tag.SUFFIX;
	}

	public boolean isNametagElement() {
		return slot == null && player != null;

	}

	public boolean isNametagPrefix() {
		return isNametagElement() && tag == Tag.PREFIX;
	}

	public boolean isNametagSuffix() {
		return isNametagElement() && tag == Tag.SUFFIX;
	}

	public boolean isBelowName() {
		return slot == DisplaySlot.BELOW_NAME && isObjective();
	}

	public boolean isScored() {
		return score != null;
	}

	public boolean isObjective() {
		return tag == Tag.OBJECTIVE_NAME;
	}

	public boolean isUnknown() {
		return !sidebarElement() && !isNametagElement() && !isBelowName();
	}

	public Destination clone() throws CloneNotSupportedException {
		return (Destination) super.clone();
	}

}
