// baseapitester.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#define _(x) (x)

int _tmain(int argc, _TCHAR* argv[])
{
	if ((argc == 2 && strcmp(argv[1], "-v") == 0) ||
		(argc == 2 && strcmp(argv[1], "--version") == 0)) {
			fprintf(stderr, "tesseract %s\n", tesseract::TessBaseAPI::Version());
			exit(0);
	}
	// Make the order of args a bit more forgiving than it used to be.
	const char* lang = "eng";
	const char* image = NULL;
	const char* output = NULL;
	tesseract::PageSegMode pagesegmode = tesseract::PSM_AUTO;
	int arg = 1;
	while (arg < argc && (output == NULL || argv[arg][0] == '-')) {
		if (strcmp(argv[arg], "-l") == 0 && arg + 1 < argc) {
			lang = argv[arg + 1];
			++arg;
		} else if (strcmp(argv[arg], "-psm") == 0 && arg + 1 < argc) {
			pagesegmode = static_cast<tesseract::PageSegMode>(atoi(argv[arg + 1]));
			++arg;
		} else if (image == NULL) {
			image = argv[arg];
		} else if (output == NULL) {
			output = argv[arg];
		}
		++arg;
	}
	if (output == NULL) {
		fprintf(stderr, _("Usage:%s imagename outputbase [-l lang] "
			"[-psm pagesegmode] [configfile...]\n"), argv[0]);
		fprintf(stderr,
			_("pagesegmode values are:\n"
			"0 = Orientation and script detection (OSD) only.\n"
			"1 = Automatic page segmentation with OSD.\n"
			"2 = Automatic page segmentation, but no OSD, or OCR\n"
			"3 = Fully automatic page segmentation, but no OSD. (Default)\n"
			"4 = Assume a single column of text of variable sizes.\n"
			"5 = Assume a single uniform block of vertically aligned text.\n"
			"6 = Assume a single uniform block of text.\n"
			"7 = Treat the image as a single text line.\n"
			"8 = Treat the image as a single word.\n"
			"9 = Treat the image as a single word in a circle.\n"
			"10 = Treat the image as a single character.\n"));
		fprintf(stderr, _("-l lang and/or -psm pagesegmode must occur before any"
			"configfile.\n"));
		exit(1);
	}

	tesseract::TessBaseAPI  api;

	api.SetOutputName(output);
	api.Init(argv[0], lang, tesseract::OEM_DEFAULT,
		&(argv[arg]), argc - arg, NULL, NULL, false);
	// We have 2 possible sources of pagesegmode: a config file and
	// the command line. For backwards compatability reasons, the
	// default in tesseract is tesseract::PSM_SINGLE_BLOCK, but the
	// default for this program is tesseract::PSM_AUTO. We will let
	// the config file take priority, so the command-line default
	// can take priority over the tesseract default, so we use the
	// value from the command line only if the retrieved mode
	// is still tesseract::PSM_SINGLE_BLOCK, indicating no change
	// in any config file. Therefore the only way to force
	// tesseract::PSM_SINGLE_BLOCK is from the command line.
	// It would be simpler if we could set the value before Init,
	// but that doesn't work.
	if (api.GetPageSegMode() == tesseract::PSM_SINGLE_BLOCK)
		api.SetPageSegMode(pagesegmode);
	printf("Tesseract Open Source OCR Engine v%s with Leptonica\n",
		tesseract::TessBaseAPI::Version());


	FILE* fin = fopen(image, "rb");
	if (fin == NULL) {
		printf("Cannot open input file: %s\n", image);
		exit(2);
	}
	fclose(fin);

	PIX   *pixs;
	if ((pixs = pixRead(image)) == NULL) {
		printf("Unsupported image type.\n");
		exit(3);
	}
	pixDestroy(&pixs);

	STRING text_out;
	if (!api.ProcessPages(image, NULL, 0, &text_out)) {
		printf("Error during processing.\n");
	}
	bool output_hocr = false;
	api.GetBoolVariable("tessedit_create_hocr", &output_hocr);
	bool output_box = false;
	api.GetBoolVariable("tessedit_create_boxfile", &output_box);
	STRING outfile = output;
	outfile += output_hocr ? ".html" : output_box ? ".box" : ".txt";
	FILE* fout = fopen(outfile.string(), "wb");
	if (fout == NULL) {
		printf("Cannot create output file %s\n", outfile.string());
		exit(1);
	}
	fwrite(text_out.string(), 1, text_out.length(), fout);
	fclose(fout);

	return 0;                      // Normal exit
}
