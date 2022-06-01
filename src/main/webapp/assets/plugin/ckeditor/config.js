/**
 * @license Copyright (c) 2003-2020, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	 config.language = 'en';
	// config.uiColor = '#AADC6E';
	
	config.toolbarGroups = [
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing', groups: [ 'selection', 'spellchecker', 'editing' ] },
		//{ name: 'forms', groups: [ 'forms' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		'/',
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'paragraph' ] },
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups:['insert'] },
		{ name: 'colors', groups: [ 'colors' ] }
		
	];
	
	config.removeButtons = "Save,NewPage,Preview,Print,Styles,Flash,SpecialChar,PageBreak,Iframe,Font,CreateDiv"; // 不要的按鈕
	config.enterMode = CKEDITOR.ENTER_BR;
        config.extraAllowedContent ="img[src,alt,width,height];";
       //config.extraPlugins = 'sourcedialog';
        config.height = '20em'; 
	
	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';
        
        config.allowedContent = true;
        
        
        //Plugin
        //config.extraPlugins = 'iframedialog';
        config.removePlugins = 'forms';
};
