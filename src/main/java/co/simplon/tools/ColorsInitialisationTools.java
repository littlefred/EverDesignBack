package co.simplon.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import co.simplon.models.Colors;
import co.simplon.repository.ColorsRepository;

@Named
public class ColorsInitialisationTools {
	@Inject
	ColorsRepository colorsRepository;

	/**
	 * method to insert a colors file
	 * 
	 * @param file
	 * @return
	 */
	public final String insertionFileColors(List<String[]> file) {
		List<Colors> listOfColors = new ArrayList<>();// open list to note colors to upload in DBB
		// initialisation of counters to follow the result of insertion file
		int nbColorsLoaded = 0;
		int nbColorsUpdated = 0;
		int nbColorsUntreated = 0;
		int nbColors = file.size();
		int nbLine = 0;
		String lines = "";
		// processing for each line of file
		for (String[] line : file) {
			nbLine++;
			Optional<Colors> colorInBase = this.colorsRepository.findBySticker(line[2]);
			if (!colorInBase.isPresent()) {
				Optional<Colors> colorInBase2 = this.colorsRepository.findByName(line[1].toLowerCase());
				if (!colorInBase2.isPresent()) {
					listOfColors.add(new Colors(Material.valueOf(line[0]), line[1], line[2]));
					nbColorsLoaded++;
				} else {
					Colors tempColor = colorInBase2.get();
					if (tempColor.getMaterial().equals(Material.valueOf(line[0]))) {
						tempColor.setSticker(line[2]);
						listOfColors.add(tempColor);
						nbColorsUpdated++;
					} else {
						nbColorsUntreated++;
						lines += "- " + nbLine + "(name already used in another material) ";
					}
				}
			} else {
				nbColorsUntreated++;
				lines += "- " + nbLine + "(picture already used) ";
			}
		}
		// after add colors that should be saved we write a resume of actions
		this.colorsRepository.saveAll(listOfColors);
		System.out.println(nbColorsLoaded + " colors loaded");
		System.out.println(nbColorsUpdated + " colors updated");
		int nbTotal = nbColorsLoaded + nbColorsUpdated;
		System.out.println("result : " + nbTotal + " colors out of " + nbColors);
		if (!lines.equals("")) {
			System.out.println("==> " + nbColorsUntreated + " colors untreated :");
			System.out.println("Lines " + lines);
		}
		return "file loaded";
	}

}
