import { CustomTheme } from '@constants/themes';
import { StyleSheet } from 'react-native';



export const HomePageStyles = (theme: CustomTheme) => StyleSheet.create({
  pageContainer: {
    flex: 1,
    backgroundColor: theme.colors.background.s200,
  },
  cardContainer: {
    top: "10%",
    left: 0,
    width: '100%',
    height: '90%',

    display: 'flex',
    //justifyContent: 'center',
    alignItems: 'center',
    gap: 20, 

  },
  card: {
    width: "90%",
    height: "85%",
    
    borderRadius: 20,
    backgroundColor: "green",
  },
  cardHeader: {
    display: "flex",
  },
  button: {
    width: 52,
    aspectRatio: 1,
    
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',

    borderRadius: 50,
    backgroundColor: theme.colors.background.s600,

    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 12,
    },
    shadowOpacity: 0.58,
    shadowRadius: 16.00,
    
    elevation: 24,
  },
  buttonPressed: {
    backgroundColor: theme.colors.background.s200,
  },
});