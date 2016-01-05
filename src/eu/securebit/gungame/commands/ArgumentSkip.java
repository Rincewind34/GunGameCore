package eu.securebit.gungame.commands;


public class ArgumentSkip /* extends CustomArgument */ {
	
//	@Override
//	public String getSyntax() {
//		return "/gungame skip";
//	}
//
//	@Override
//	public String getPermission() {
//		return Permissions.commandGunGameSkip();
//	}
//
//	@Override
//	public boolean isOnlyForPlayer() {
//		return false;
//	}
//
//	@Override
//	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
//		if (args.length == 1) {
//			if (!(Main.instance().getGameStateManager().getCurrent() instanceof DisabledStateEdit)) {
//				Main.instance().getGameStateManager().next();
//				
//				sender.sendMessage(Messages.gamestateSkiped());
//				sender.sendMessage(Messages.currentGamestate());
//			} else {
//				sender.sendMessage(Messages.wrongMode("EditMode"));
//			}
//		} else {
//			sender.sendMessage(Messages.syntax(this.getSyntax()));
//		}
//		
//		return true;
//	}
//
//	@Override
//	public void stageInformation(InfoLayout layout) {
//		layout.line("Skips the current gamestate and jumps to the next.");
//	}

}
