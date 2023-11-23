import { StyleSheet } from "react-native";
import {CustomTheme, DarkTheme} from '@constants/themes';


export const styles = (theme: CustomTheme) => StyleSheet.create({
  container: {
    width: "80%",
    position: "absolute",
    alignSelf: "center",
    bottom: 16,
    
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",

    borderRadius: 20,
    paddingVertical: 10,
    paddingHorizontal: 20,

    backgroundColor: theme.colors.background.brand,

    // https://ethercreative.github.io/react-native-shadow-generator/
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 12,
    },
    shadowOpacity: 0.58,
    shadowRadius: 16.00,
    
    elevation: 24,
  },
  button: {
    display: 'flex',
    justifyContent: 'center',
    gap: 4,
  },
  iconWrapper: {
    width: 50,
    aspectRatio: 1,
    borderRadius: 50,

    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    
    backgroundColor: theme.colors.background.s600,

    zIndex: 1,
  },
  iconWrapperPressed: {
    backgroundColor: theme.colors.background.s200,
  },
  text: {
    fontSize: 10,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});
