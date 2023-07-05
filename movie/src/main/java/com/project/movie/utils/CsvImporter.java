//package com.project.movie.utils;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//
//public class CsvImporter {
//	private static final String INSERT_SQL = "INSERT INTO Movie (movieId, movieName, overview, producer, rating, releaseDate) VALUES (?, ?, ?, ?, ?, ?)";
//
//	public static void importCsvData(String csvFilePath) {
//		try (Connection connection = DatabaseConnection.getConnection();
//			 BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8));
//			 PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
//
//			String line;
//			int i = 0;
//
////			int tmp = 0;
//			reader.readLine();
//			while ((line = reader.readLine()) != null) {
////				if(tmp++ != 9) continue; // test
//
//
//				int flag = 0;
//				boolean adult;
//				String belongsToCollection;
//				int budget;
//				if(Character.isDigit(line.charAt(7)) || Character.isDigit(line.charAt(8)))
//					flag = 2;
//				String[] data = line.replace("\"\"", "\\*").split("\"");
//				if(flag == 2) {
//					String[] data0 = data[0].split(",");
//					adult = Boolean.parseBoolean(data0[0]);
//					belongsToCollection = data0[1];
//					budget = Integer.parseInt(data0[2]);
//				}
//				else {
//					adult = Boolean.parseBoolean(data[0]); // adult
//					belongsToCollection = data[1]; // belongs_to_collection
//					budget = Integer.parseInt(data[2].replace(",", "")); // budget
//				}
//				String genres = data[3 - flag]; //genres
//
//				String[] data2 = data[4 - flag].substring(1).split(",");
//				String homepage = data2[0]; // homepage
//				int id = Integer.parseInt(data2[1]); // movieId
//				String imdbId = data2[2];
//				String originalLanguage = data2[3];
//				String originalTitle = data[4];
//
//				String overview = data[5 - flag];
//
//				String[] data3 = data[6 - flag].substring(1, data[6 - flag].length() - 1).split(",");
//				double popularity = Double.parseDouble(data3[0]);
//				String posterPath = data3[1];
//
//				String productionCompanies = getFirstItem(data[7 - flag]);
//				String productionCountries = data[9 - flag];
//
//				String[] data4 = data[10 - flag].substring(1, data[10 - flag].length() - 1).split(",");
//				String releaseDate = data4[0];
//				int revenue = Integer.parseInt(data4[1]);
//				int runtime = Integer.parseInt(data4[2]);
//
//				String spokenLanguages = data[11 - flag];
//
//				String[] data5 = data[12 - flag].substring(1).split(",");
//				String status = data5[0];
//				String tagline = data5[1];
//				String title = data5[2];
//				String video = data5[3];
//				double voteAverage = Double.parseDouble(data5[4]);
//				int voteCount = Integer.parseInt(data5[5]);
//
//				statement.setInt(1, id);
//				statement.setString(2, title);
//				statement.setString(3, overview);
//				statement.setString(4, productionCompanies);
//				statement.setDouble(5, voteAverage);
//				statement.setString(6, releaseDate);
//
//				statement.executeUpdate();
//				System.out.println("Inset Line " + i++);
//			}
//
//			System.out.println("Data imported successfully.");
//		} catch (IOException | SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static String getFirstItem(String jsonString) {
//		if(jsonString.equals("[]")) return jsonString;
//		return jsonString
//				.replace("\\[", "")
//				.replace("\\]", "")
//				.split("}")[0]
//				+ "}";
//	}
//
//	public static void main(String[] args) {
////		String csvFilePath = "path/to/your.csv";
//		String csvFilePath = "/Users/jackwu/Desktop/HKU/Project/movies_metadata.csv";
//		importCsvData(csvFilePath);
//	}
//}
//
