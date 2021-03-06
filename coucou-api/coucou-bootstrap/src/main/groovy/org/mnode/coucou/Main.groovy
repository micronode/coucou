package org.mnode.coucou

import org.mnode.ousia.DialogExceptionHandler
import org.mnode.ousia.OusiaBuilder
import org.mnode.ousia.flamingo.icons.LogoSvgIcon
import org.pushingpixels.substance.api.SubstanceConstants
import org.pushingpixels.substance.api.SubstanceLookAndFeel
import org.pushingpixels.substance.api.fonts.SubstanceFontUtilities

import javax.swing.*
import javax.swing.UIManager.LookAndFeelInfo

try {
	new Socket('localhost', 1337)
	println 'Already running'
	System.exit(0)
}
catch (Exception e) {
}

// Install look and feels..
UIManager.put(SubstanceLookAndFeel.TABBED_PANE_CONTENT_BORDER_KIND, SubstanceConstants.TabContentPaneBorderKind.SINGLE_FULL)
UIManager.installLookAndFeel(new LookAndFeelInfo('Substance Nebula', 'substance-nebula'))
UIManager.installLookAndFeel(new LookAndFeelInfo('Substance Office Blue 2007', 'substance-office-blue-2007'))
UIManager.installLookAndFeel(new LookAndFeelInfo('Substance Office Silver 2007', 'substance-office-silver-2007'))
UIManager.installLookAndFeel(new LookAndFeelInfo('Substance Mariner', 'substance-mariner'))
UIManager.installLookAndFeel(new LookAndFeelInfo('Substance Business Black Steel', 'substance-business-black-steel'))
UIManager.installLookAndFeel(new LookAndFeelInfo('Substance Business Blue Steel', 'substance-business-blue-steel'))
UIManager.installLookAndFeel(new LookAndFeelInfo('Substance Raven', 'substance-raven'))

///


OusiaBuilder ousia = []
Thread.start {
	ServerSocket server = [1337]
	while(true) {
		try {
			server.accept {}
		}
		finally {
			ousia.doLater {
				frame.visible = true
			}
		}
	}
}

def actionContext = [:] as ObservableMap

ousia.edt {
	lookAndFeel(prefs(Main).get('lookAndFeel', 'system')) //.fontPolicy = SubstanceFontUtilities.getScaledFontPolicy(1.2)
	println "Look and Feel: $UIManager.lookAndFeel.name"
//	lookAndFeel('substance-office-blue-2007').fontPolicy = SubstanceFontUtilities.getScaledFontPolicy(1.2)
    if (UIManager.lookAndFeel instanceof SubstanceLookAndFeel) {
        SubstanceLookAndFeel.fontPolicy = SubstanceFontUtilities.getScaledFontPolicy(prefs(Main).getInt('scalingFactor', 10) / 10)
    }
	
	fileChooser(id: 'dirChooser', fileSelectionMode: JFileChooser.FILES_AND_DIRECTORIES)
	
	def frameIconImages = [
		imageIcon('/globe64.png').image,
		imageIcon('/globe48.png').image,
		imageIcon('/globe32.png').image,
		imageIcon('/globe16.png').image
	]
	
//	resizableIcon('/logo.svg', size: [20, 20], id: 'logoIcon')
//	def applicationIcon = ImageWrapperResizableIcon.getIcon(Main.getResource('/logo-48.png'), [20, 20] as Dimension)
	def applicationIcon = new LogoSvgIcon()
	
	frame(new RibbonWindow(ousia), id: 'frame', title: 'Coucou', size: [640, 400], locationRelativeTo: null,
		visible: true, defaultCloseOperation: JFrame.EXIT_ON_CLOSE, iconImages: frameIconImages,
		applicationIcon: applicationIcon, trackingEnabled: true)
	
	Thread.defaultUncaughtExceptionHandler = new DialogExceptionHandler(dialogOwner: frame)
	Authenticator.default = new org.mnode.ousia.DialogAuthenticator(frame)
}
