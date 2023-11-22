import { View, StyleSheet } from "react-native";
import {CustomTheme, DarkTheme} from '@constants/themes';


export const styles = (theme: CustomTheme) => StyleSheet.create({
  container: {
    width: 160 * 2,
    height: 80,
    bottom: 10,
    
    position: "absolute",
    alignSelf: "center",

    borderRadius: 20,

    backgroundColor: theme.colors.background.brand,
  },
  button1: {
    width: 52,
    height: 52,
    postition: "relative",
    alignSelf: "center",
    transform: [{translateX: -100}, {translateY:10}],

    justifyContent: 'center',
    borderRadius: 100,
    elevation: 3,
    backgroundColor: "#414361",
  },
  button2: {
    width: 52,
    height: 52,
    postition: "absolute",
    alignSelf: "center",
    transform: [{translateX: 0}, {translateY:-42}],

    justifyContent: 'center',
    borderRadius: 100,
    elevation: 3,
    backgroundColor: "#414361",
  },
  button3: {
    width: 52,
    height: 52,
    postition: "relative",
    alignSelf: "center",
    transform: [{translateX: 100}, {translateY:-94}],

    justifyContent: 'center',
    borderRadius: 100,
    elevation: 3,
    backgroundColor: "#414361",
  },
  icon: {
    postition: "relative",
    transform: [{translateX: 11.5}, {translateY:0}],
    justifyContent: 'center',
    elevation: 3,
    opacity: 1,
  },
  text: {
    bottom:1.5,
    fontSize: 10,
    lineHeight: 21,
    fontWeight: 'bold',
    letterSpacing: 0.25,
    color: 'white',
    position: "absolute",
    alignSelf: "center",
    transform: [{translateY: 20}],
  },
});
